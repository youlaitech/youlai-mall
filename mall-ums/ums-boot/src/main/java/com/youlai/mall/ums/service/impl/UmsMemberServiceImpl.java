package com.youlai.mall.ums.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.result.ResultCode;
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.pms.model.vo.ProductHistoryVO;
import com.youlai.common.constant.MemberConstants;
import com.youlai.mall.ums.convert.AddressConvert;
import com.youlai.mall.ums.convert.MemberConvert;
import com.youlai.mall.ums.dto.MemberAddressDTO;
import com.youlai.mall.ums.dto.MemberAuthDTO;
import com.youlai.mall.ums.dto.MemberRegisterDto;
import com.youlai.mall.ums.mapper.UmsMemberMapper;
import com.youlai.mall.ums.model.entity.UmsAddress;
import com.youlai.mall.ums.model.entity.UmsMember;
import com.youlai.mall.ums.model.vo.MemberVO;
import com.youlai.mall.ums.service.UmsAddressService;
import com.youlai.mall.ums.service.UmsMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 会员业务实现类
 *
 * @author haoxr
 * @since 2022/2/12
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {

    private final RedisTemplate redisTemplate;
    private final MemberConvert memberConvert;

    private final AddressConvert addressConvert;
    private final UmsAddressService addressService;

    @Override
    public IPage<UmsMember> list(Page<UmsMember> page, String nickname) {
        List<UmsMember> list = this.baseMapper.list(page, nickname);
        page.setRecords(list);
        return page;
    }

    @Override
    public void addProductViewHistory(ProductHistoryVO product, Long userId) {
        if (userId != null) {
            String key = MemberConstants.USER_PRODUCT_HISTORY + userId;
            redisTemplate.opsForZSet().add(key, product, System.currentTimeMillis());
            Long size = redisTemplate.opsForZSet().size(key);
            if (size > 10) {
                redisTemplate.opsForZSet().removeRange(key, 0, size - 11);
            }
        }
    }

    @Override
    public Set<ProductHistoryVO> getProductViewHistory(Long userId) {
        return redisTemplate.opsForZSet().reverseRange(MemberConstants.USER_PRODUCT_HISTORY + userId, 0, 9);
    }

    /**
     * 根据 openid 获取会员认证信息
     *
     * @param openid 微信唯一身份标识
     * @return
     */
    @Override
    public MemberAuthDTO getMemberByOpenid(String openid) {
        UmsMember entity = this.getOne(new LambdaQueryWrapper<UmsMember>()
                .eq(UmsMember::getOpenid, openid)
                .select(UmsMember::getId,
                        UmsMember::getOpenid,
                        UmsMember::getStatus
                )
        );
        
        if (entity == null) {
            throw new BizException(ResultCode.USER_NOT_EXIST);
        }
        return memberConvert.entity2OpenidAuthDTO(entity);
    }

    /**
     * 根据手机号获取会员认证信息
     *
     * @param mobile
     * @return
     */
    @Override
    public MemberAuthDTO getMemberByMobile(String mobile) {
        UmsMember entity = this.getOne(new LambdaQueryWrapper<UmsMember>()
                .eq(UmsMember::getMobile, mobile)
                .select(UmsMember::getId,
                        UmsMember::getMobile,
                        UmsMember::getStatus
                )
        );

        if (entity == null) {
            throw new BizException(ResultCode.USER_NOT_EXIST);
        }
        return memberConvert.entity2MobileAuthDTO(entity);
    }

    /**
     * 新增会员
     *
     * @param memberRegisterDTO
     * @return
     */
    @Override
    public Long addMember(MemberRegisterDto memberRegisterDTO) {
        UmsMember umsMember = memberConvert.dto2Entity(memberRegisterDTO);
        boolean result = this.save(umsMember);
        Assert.isTrue(result, "新增会员失败");
        return umsMember.getId();
    }

    /**
     * 获取登录会员信息
     *
     * @return
     */
    @Override
    public MemberVO getCurrMemberInfo() {
        Long memberId = SecurityUtils.getMemberId();
        UmsMember umsMember = this.getOne(new LambdaQueryWrapper<UmsMember>()
                .eq(UmsMember::getId, memberId)
                .select(UmsMember::getId,
                        UmsMember::getNickName,
                        UmsMember::getAvatarUrl,
                        UmsMember::getMobile,
                        UmsMember::getBalance
                )
        );
        MemberVO memberVO = new MemberVO();
        BeanUtil.copyProperties(umsMember, memberVO);
        return memberVO;
    }

    /**
     * 获取会员地址
     *
     * @param memberId
     * @return
     */
    @Override
    public List<MemberAddressDTO> listMemberAddress(Long memberId) {

        List<UmsAddress> entities = addressService.list(
                new LambdaQueryWrapper<UmsAddress>()
                        .eq(UmsAddress::getMemberId, memberId)
        );

        List<MemberAddressDTO> list = addressConvert.entity2Dto(entities);
        return list;
    }
}
