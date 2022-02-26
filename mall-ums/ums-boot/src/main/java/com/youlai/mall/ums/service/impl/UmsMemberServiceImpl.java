package com.youlai.mall.ums.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.result.ResultCode;
import com.youlai.common.web.exception.BizException;
import com.youlai.common.web.util.MemberUtils;
import com.youlai.mall.pms.pojo.vo.ProductHistoryVO;
import com.youlai.mall.ums.constant.UmsConstants;
import com.youlai.mall.ums.dto.MemberAuthInfoDTO;
import com.youlai.mall.ums.dto.MemberDTO;
import com.youlai.mall.ums.mapper.UmsMemberMapper;
import com.youlai.mall.ums.pojo.entity.UmsMember;
import com.youlai.mall.ums.pojo.vo.MemberVO;
import com.youlai.mall.ums.service.IUmsMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 会员业务实现类
 *
 * @author haoxr
 * @date 2022/2/12
 */
@Service
@RequiredArgsConstructor
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements IUmsMemberService {

    private final RedisTemplate redisTemplate;

    @Override
    public IPage<UmsMember> list(Page<UmsMember> page, String nickname) {
        List<UmsMember> list = this.baseMapper.list(page, nickname);
        page.setRecords(list);
        return page;
    }

    @Override
    public void addProductViewHistory(ProductHistoryVO product, Long userId) {
        if (userId != null) {
            String key = UmsConstants.USER_PRODUCT_HISTORY + userId;
            redisTemplate.opsForZSet().add(key, product, System.currentTimeMillis());
            Long size = redisTemplate.opsForZSet().size(key);
            if (size > 10) {
                redisTemplate.opsForZSet().removeRange(key, 0, size - 11);
            }
        }
    }

    @Override
    public Set<ProductHistoryVO> getProductViewHistory(Long userId) {
        return redisTemplate.opsForZSet().reverseRange(UmsConstants.USER_PRODUCT_HISTORY + userId, 0, 9);
    }

    /**
     * 根据 openid 获取会员认证信息
     *
     * @param openid
     * @return
     */
    @Override
    public MemberAuthInfoDTO getByOpenid(String openid) {
        UmsMember member = this.getOne(new LambdaQueryWrapper<UmsMember>()
                .eq(UmsMember::getOpenid, openid)
                .select(UmsMember::getId,
                        UmsMember::getOpenid,
                        UmsMember::getStatus
                )
        );
        MemberAuthInfoDTO memberAuth = null;
        if (member != null) {
            memberAuth = new MemberAuthInfoDTO()
                    .setMemberId(member.getId())
                    .setUsername(member.getOpenid())
                    .setStatus(member.getStatus());
        }
        return memberAuth;
    }

    /**
     * 根据手机号获取会员认证信息
     *
     * @param mobile
     * @return
     */
    @Override
    public MemberAuthInfoDTO getByMobile(String mobile) {
        UmsMember member = this.getOne(new LambdaQueryWrapper<UmsMember>()
                .eq(UmsMember::getMobile, mobile)
                .select(UmsMember::getId,
                        UmsMember::getOpenid,
                        UmsMember::getStatus
                )
        );

        MemberAuthInfoDTO memberAuth = null;
        if (member != null) {
            memberAuth = new MemberAuthInfoDTO()
                    .setMemberId(member.getId())
                    .setUsername(member.getMobile())
                    .setStatus(member.getStatus());
        }
        return memberAuth;
    }

    /**
     * 新增会员
     *
     * @param memberDTO
     * @return
     */
    @Override
    public Long addMember(MemberDTO memberDTO) {
        UmsMember umsMember = new UmsMember();
        BeanUtil.copyProperties(memberDTO, umsMember);
        umsMember.setStatus(GlobalConstants.STATUS_YES);

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
    public MemberVO getCurrentMemberInfo() {
        Long memberId = MemberUtils.getMemberId();
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
}
