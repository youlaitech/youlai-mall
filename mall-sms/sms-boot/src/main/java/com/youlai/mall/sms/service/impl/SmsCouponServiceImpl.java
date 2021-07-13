package com.youlai.mall.sms.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.util.BeanMapperUtils;
import com.youlai.mall.sms.mapper.SmsCouponMapper;
import com.youlai.mall.sms.pojo.domain.SmsCoupon;
import com.youlai.mall.sms.pojo.domain.SmsCouponTemplate;
import com.youlai.mall.sms.pojo.enums.CouponStateEnum;
import com.youlai.mall.sms.pojo.form.CouponForm;
import com.youlai.mall.sms.pojo.query.CouponPageQuery;
import com.youlai.mall.sms.pojo.vo.CouponClassify;
import com.youlai.mall.sms.pojo.vo.CouponTemplateVO;
import com.youlai.mall.sms.pojo.vo.SmsCouponVO;
import com.youlai.mall.sms.service.ICouponRedisService;
import com.youlai.mall.sms.service.ISmsCouponService;
import com.youlai.mall.sms.service.ISmsCouponTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author huawei
 * @desc 优惠券服务业务实现类
 * @email huawei_code@163.com
 * @date 2021/3/14
 */
@Service
@Slf4j
public class SmsCouponServiceImpl extends ServiceImpl<SmsCouponMapper, SmsCoupon> implements ISmsCouponService {

    @Autowired
    private ICouponRedisService couponRedisService;

    @Autowired
    private ISmsCouponTemplateService couponTemplateService;

    @Override
    public List<CouponTemplateVO> findAvailableTemplate(Long userId) {

        // 1、查询所有可用优惠券模板
        List<SmsCouponTemplate> templates = couponTemplateService.findAllUsableTemplate(1, 1);
        Date nowTime = new Date();
        templates = templates.stream()
                .filter(template -> template.getRule().getExpiration().getDeadline() < nowTime.getTime())
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(templates)) {
            log.info("All Usable Template is Empty.");
            return new ArrayList<>();
        }
        log.info("All Usable Coupon Template Size={}", templates.size());

        // 2、查询用户已领取优惠券数量
        List<SmsCoupon> coupons = findCouponsByState(userId, 0);
        Map<Long, List<SmsCoupon>> couponMap = coupons.stream().collect(Collectors.groupingBy(SmsCoupon::getTemplateId));
        log.info("All User Receive Coupon Template Size={}", couponMap.size());

        // 3、过滤用户已领取优惠券模板
        templates = templates.stream().filter(template -> {
            List<SmsCoupon> receivedCoupons = couponMap.getOrDefault(template.getId(), new ArrayList<>());
            return template.getRule().getLimitation() > receivedCoupons.size();
        }).collect(Collectors.toList());
        log.info("Find All Available Template Size={}", templates.size());

        return BeanMapperUtils.mapList(templates, CouponTemplateVO.class);
    }

    @Override
    public void receive(Long userId, String templateId) {

    }

    @Override
    public List<SmsCoupon> findCouponsByState(Long userId, Integer state) {
        log.info("根据查询优惠券列表，UserId={}, State={}", userId, state);
        List<SmsCoupon> cachedCoupons = couponRedisService.getCachedCoupons(userId, state);
        List<SmsCoupon> targetCoupon = new ArrayList<>();
        if (!CollUtil.isEmpty(cachedCoupons)) {
            // 如果缓存中有数据直接取缓存中的数据
            targetCoupon = cachedCoupons;
        } else {
            // 如果缓存中没有数据，从数据库取数据
            QueryWrapper<SmsCoupon> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            queryWrapper.eq("state", state);
            List<SmsCoupon> dbCoupon = this.list(queryWrapper);
            if (CollUtil.isEmpty(dbCoupon)) {
                log.info("Current User Do Not Has Coupon: {} {}", userId, state);
                return dbCoupon;
            }
            List<SmsCouponTemplate> templates = couponTemplateService.listByIds(dbCoupon.stream().map(SmsCoupon::getTemplateId).collect(Collectors.toList()));
            Map<Long, SmsCouponTemplate> templateMap = templates.stream().collect(Collectors.toMap(SmsCouponTemplate::getId, Function.identity()));
            dbCoupon.forEach(db -> db.setTemplate(templateMap.get(db.getTemplateId())));
            targetCoupon = dbCoupon;
            couponRedisService.addCouponToCache(userId, targetCoupon, state);
            log.info("Add User Coupons To Cache, {} {}", userId, state);
        }

        // 将无效的优惠券剔除
        targetCoupon = targetCoupon.stream().filter(c -> c.getState().getCode() != -1).collect(Collectors.toList());
        if (CouponStateEnum.of(state) == CouponStateEnum.USABLE) {
            // 如果当前获取的是可用优惠券，还需要对已过期优惠券进行延时处理
            CouponClassify classify = CouponClassify.classify(targetCoupon);
            if (!CollUtil.isEmpty(classify.getExpired())) {
                log.info("Add User Expired Coupons To Cache, {} {}", userId, state);
                couponRedisService.addCouponToCache(userId, classify.getExpired(), CouponStateEnum.EXPIRED.getCode());
                this.updateBatchById(classify.getExpired());
            }
        }
        return targetCoupon;
    }

    @Override
    public SmsCouponVO detail(String couponId) {
        log.info("根据优惠券ID获取优惠券详情，couponId={}", couponId);
        SmsCoupon coupon = this.getById(couponId);
        return BeanMapperUtils.map(coupon, SmsCouponVO.class);
    }

    @Override
    public void add(CouponForm form) {
        log.info("新增优惠券，form={}", form);
        SmsCoupon coupon = BeanMapperUtils.map(form, SmsCoupon.class);
        coupon.setId(null);
        this.save(coupon);
    }

    @Override
    public void modify(CouponForm form) {
        log.info("新增优惠券，form={}", form);
        SmsCoupon coupon = BeanMapperUtils.map(form, SmsCoupon.class);
        // TODO 如果该优惠券已经有领取记录，则优惠券价格、限领账户、过期时间等相关参数不能修改
        // 这里没有考虑太复杂，如有需要自行处理
        this.updateById(coupon);
    }

    @Override
    public IPage<SmsCoupon> pageQuery(CouponPageQuery query) {
        log.info("Page Query Coupons，TemplateID={}，query={}", query.getTemplateId(), query);
        Page<SmsCoupon> page = new Page<>(query.getPageNum(), query.getPageSize());
        QueryWrapper<SmsCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("template_id", query.getTemplateId());
        queryWrapper.eq(StrUtil.isNotBlank(query.getCouponCode()), "coupon_code", query.getCouponCode());
        queryWrapper.eq(query.getState() != null, "state", query.getState());
        queryWrapper.orderByDesc("gmt_create");
        return this.page(page, queryWrapper);
    }

    @Override
    public int updateTakeStock(String couponId) {
        return this.updateTakeStock(couponId);
    }
}
