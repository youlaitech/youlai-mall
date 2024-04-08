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
import com.youlai.mall.ums.convert.AddressConvert;
import com.youlai.mall.ums.convert.MemberConvert;
import com.youlai.mall.ums.dto.MemberAddressDTO;
import com.youlai.mall.ums.dto.MemberAuthDTO;
import com.youlai.mall.ums.dto.MemberRegisterDTO;
import com.youlai.mall.ums.mapper.UmsMemberMapper;
import com.youlai.mall.ums.model.bo.MemberBO;
import com.youlai.mall.ums.model.entity.UmsAddress;
import com.youlai.mall.ums.model.entity.UmsMember;
import com.youlai.mall.ums.model.dto.MemberDTO;
import com.youlai.mall.ums.model.query.MemberPageQuery;
import com.youlai.mall.ums.model.vo.MemberPageVO;
import com.youlai.mall.ums.service.UmsAddressService;
import com.youlai.mall.ums.service.UmsMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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


    private final UmsAddressService addressService;
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
        return memberConvert.bo2PageVo(boPage);
    }

    /**
     * 根据 openid 获取会员认证信息
     *
     * @param openid 微信唯一身份标识
     * @return 会员认证信息
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
     * @param mobile 手机号
     * @return 会员认证信息
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
     * 注册会员
     */
    @Override
    public Long addMember(MemberRegisterDTO registerDto) {
        UmsMember entity = memberConvert.registerDto2Entity(registerDto);
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
        UmsMember umsMember = this.getOne(new LambdaQueryWrapper<UmsMember>()
                .eq(UmsMember::getId, memberId)
                .select(UmsMember::getId,
                        UmsMember::getNickName,
                        UmsMember::getAvatarUrl,
                        UmsMember::getMobile,
                        UmsMember::getBalance
                )
        );
        MemberDTO memberDTO = new MemberDTO();
        BeanUtil.copyProperties(umsMember, memberDTO);
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
        List<UmsAddress> entities = addressService.list(new LambdaQueryWrapper<UmsAddress>()
                .eq(UmsAddress::getMemberId, memberId)
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
        UmsMember member = this.getById(memberId);
       Assert.isTrue(member.getBalance() >= deductionAmount, "会员账户余额不足");
        member.setBalance(member.getBalance() - deductionAmount);
        return this.updateById(member);
    }
}
