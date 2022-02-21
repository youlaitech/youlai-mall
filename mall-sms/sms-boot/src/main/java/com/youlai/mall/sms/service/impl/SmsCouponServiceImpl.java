package com.youlai.mall.sms.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.exception.BizException;
import com.youlai.common.web.util.MemberUtils;
import com.youlai.mall.sms.util.BeanMapperUtils;
import com.youlai.common.web.util.JwtUtils;
import com.youlai.mall.sms.mapper.SmsCouponMapper;
import com.youlai.mall.sms.pojo.domain.CouponTemplateRule;
import com.youlai.mall.sms.pojo.domain.SmsCoupon;
import com.youlai.mall.sms.pojo.domain.SmsCouponTemplate;
import com.youlai.mall.sms.pojo.enums.CouponOfferStateEnum;
import com.youlai.mall.sms.pojo.enums.CouponStateEnum;
import com.youlai.mall.sms.pojo.enums.UserTypeEnum;
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

import java.util.*;
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

        Set<Integer> userTypes = new HashSet<>();
        userTypes.add(UserTypeEnum.ALL.getCode());
        // 1、正在发放中的优惠券模板
        List<SmsCouponTemplate> templates = couponTemplateService.findAllOfferingTemplate(userTypes);
        if (CollUtil.isEmpty(templates)) {
            log.info("All Usable Template is Empty.");
            return new ArrayList<>();
        }

        // 2、过滤用户已领取,并且领取数量等于限制的优惠券模板
        log.info("All Usable Coupon Template Size={}", templates.size());
        List<SmsCoupon> coupons = findCouponsByState(userId, 0);
        Map<Long, List<SmsCoupon>> couponMap = coupons.stream().collect(Collectors.groupingBy(SmsCoupon::getTemplateId));
        log.info("All User Receive Coupon Template Size={}", couponMap.size());
        templates = templates.stream().filter(template -> {
            List<SmsCoupon> receivedCoupons = couponMap.getOrDefault(template.getId(), new ArrayList<>());
            return template.getRule().getUserLimit().getLimit() > receivedCoupons.size();
        }).collect(Collectors.toList());
        log.info("Find All Available Template Size={}", templates.size());

        return BeanMapperUtils.mapList(templates, CouponTemplateVO.class);
    }

    @Override
    public void receive(Long userId, String templateId) {
        // 1、校验优惠券发放状态
        SmsCouponTemplate template = couponTemplateService.findByTemplateId(templateId);
        long nowTime = System.currentTimeMillis();
        if (template.getOfferState() != CouponOfferStateEnum.GOING || nowTime > template.getOfferEndTime()) {
            throw new BizException("当前优惠券不在领取时间范围");
        }

        // 2、校验用户领取状态
        List<SmsCoupon> coupons = this.findByTemplateId(userId, templateId);
        if (template.getRule().getUserLimit().getLimit() <= coupons.size()) {
            log.warn("Cannot Receive Coupon Template, Max Limit, UserId={}", userId);
            throw new BizException("已达到领取优惠券最大限制");
        }

        // 3、查看该优惠券剩余优惠券码
        String couponCode = couponRedisService.tryToAcquireCouponCodeFromCache(templateId);
        if (StrUtil.isEmpty(couponCode)) {
            throw new BizException("优惠券已抢光");
        }

        // 4、生成优惠券领取记录
        saveCouponRecord(template, couponCode);
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

    /**
     * 生成优惠券模板领取记录
     *
     * @param template
     * @param couponCode
     */
    private boolean saveCouponRecord(SmsCouponTemplate template, String couponCode) {
        log.info("Create Coupon Records, TemplateId={}, CouponCode={}", template.getId(), couponCode);
        SmsCoupon coupon = new SmsCoupon();
        coupon.setTemplateId(template.getId());
        coupon.setCouponCode(couponCode);
        coupon.setUserId(MemberUtils.getMemberId());
        coupon.setUserName(MemberUtils.getUsername());
        coupon.setState(CouponStateEnum.USABLE);
        CouponTemplateRule.Expiration expiration = template.getRule().getExpiration();
        if (expiration.getPeriod() == 1) {
            coupon.setAvailableStartTime(expiration.getStartTime());
            coupon.setAvailableStartTime(expiration.getEndTime());
        } else {
            // 如果以领取之日起几天内有效，需注意最大有效天数不能大于优惠券模板过期时间
            DateTime today = DateUtil.beginOfDay(new Date());
            coupon.setAvailableStartTime(today.getTime());
            DateTime dateTime = DateUtil.offsetDay(today, expiration.getGap());
            coupon.setAvailableEndTime(dateTime.getTime() < template.getUsedEndTime() ? dateTime.getTime() : template.getUsedEndTime());
        }
        return save(coupon);
    }


    /**
     * 查询优惠券领取记录
     *
     * @param userId     用户ID
     * @param templateId 优惠券模板ID
     * @return
     */
    private List<SmsCoupon> findByTemplateId(Long userId, String templateId) {
        QueryWrapper<SmsCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("template_id", templateId);
        return list(queryWrapper);
    }
}
