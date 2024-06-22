package com.youlai.mall.member.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.result.ResultCode;
import com.youlai.common.security.util.SecurityUtils;
import com.youlai.common.web.exception.BusinessException;
import com.youlai.mall.member.convert.AddressConvert;
import com.youlai.mall.member.convert.MemberConvert;
import com.youlai.mall.member.dto.MemberAddressDTO;
import com.youlai.mall.member.dto.MemberAuthDTO;
import com.youlai.mall.member.dto.MemberRegisterDTO;
import com.youlai.mall.member.mapper.MemberMapper;
import com.youlai.mall.member.model.bo.MemberBO;
import com.youlai.mall.member.model.entity.Address;
import com.youlai.mall.member.model.entity.Member;
import com.youlai.mall.member.model.dto.MemberDTO;
import com.youlai.mall.member.model.query.MemberPageQuery;
import com.youlai.mall.member.model.vo.MemberPageVO;
import com.youlai.mall.member.service.AddressService;
import com.youlai.mall.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员业务实现类
 *
 * @author Ray
 * @since 2022/2/12
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {


    private final AddressService addressService;
    private final MemberConvert memberConvert;
    private final AddressConvert addressConvert;

    /**
     * 会员分页列表
     *
     * @return 会员分页列表
     */
    @Override
    public IPage<MemberPageVO> listPagedMembers(MemberPageQuery queryParams) {
        Page<MemberBO> boPage = this.baseMapper.listPagedMembers(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
        return memberConvert.toPageVo(boPage);
    }

    /**
     * 根据 openid 获取会员认证信息
     *
     * @param openid 微信唯一身份标识
     * @return 会员认证信息
     */
    @Override
    public MemberAuthDTO getMemberByOpenid(String openid) {
        Member entity = this.getOne(new LambdaQueryWrapper<Member>()
                .eq(Member::getOpenid, openid)
                .select(Member::getId,
                        Member::getOpenid,
                        Member::getStatus
                )
        );

        if (entity == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }
        return memberConvert.entity2OpenidAuthDTO(entity);
    }

    /**
     * 根据手机号获取会员认证信息
     *
     * @param mobile 手机号
     * @return 会员认证信息
     */
    @Override
    public MemberAuthDTO getMemberByMobile(String mobile) {
        Member entity = this.getOne(new LambdaQueryWrapper<Member>()
                .eq(Member::getMobile, mobile)
                .select(Member::getId,
                        Member::getMobile,
                        Member::getStatus
                )
        );

        if (entity == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }
        return memberConvert.entity2MobileAuthDTO(entity);
    }

    /**
     * 注册会员
     */
    @Override
    public Long addMember(MemberRegisterDTO registerDto) {
        Member entity = memberConvert.registerDto2Entity(registerDto);
        boolean result = this.save(entity);
        Assert.isTrue(result, "注册新增会员失败");
        return entity.getId();
    }

    /**
     * 获取当前登录会员信息
     */
    @Override
    public MemberDTO getCurrMemberInfo() {
        Long memberId = SecurityUtils.getMemberId();
        Member member = this.getOne(new LambdaQueryWrapper<Member>()
                .eq(Member::getId, memberId)
                .select(Member::getId,
                        Member::getNickName,
                        Member::getAvatarUrl,
                        Member::getMobile,
                        Member::getBalance
                )
        );
        MemberDTO memberDTO = new MemberDTO();
        BeanUtil.copyProperties(member, memberDTO);
        return memberDTO;
    }

    /**
     * 获取会员地址
     *
     * @param memberId 会员ID
     * @return 会员地址列表
     */
    @Override
    public List<MemberAddressDTO> listMemberAddress(Long memberId) {
        List<Address> entities = addressService.list(new LambdaQueryWrapper<Address>()
                .eq(Address::getMemberId, memberId)
        );
        return addressConvert.entity2Dto(entities);
    }

    /**
     * 扣减会员余额
     *
     * @param memberId       会员ID
     * @param deductionAmount 扣减金额(分)
     * @return 是否扣减成功
     */
    @Override
    public boolean deductMemberBalance(Long memberId, Long deductionAmount) {
        Member member = this.getById(memberId);
       Assert.isTrue(member.getBalance() >= deductionAmount, "会员账户余额不足");
        member.setBalance(member.getBalance() - deductionAmount);
        return this.updateById(member);
    }
}
