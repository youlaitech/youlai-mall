package com.youlai.mall.member.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.member.dto.MemberAddressDTO;
import com.youlai.mall.member.dto.MemberAuthDTO;
import com.youlai.mall.member.dto.MemberRegisterDTO;
import com.youlai.mall.member.model.dto.MemberDTO;
import com.youlai.mall.member.model.entity.Member;
import com.youlai.mall.member.model.query.MemberPageQuery;
import com.youlai.mall.member.model.vo.MemberPageVO;

import java.util.List;

/**
 * 会员业务接口
 *
 * @author Ray
 * @since 2022/2/12
 */
public interface MemberService extends IService<Member> {


    /**
     * 会员分页列表
     */
    IPage<MemberPageVO> getMemberPage(MemberPageQuery queryParams);


    /**
     * 根据 openid 获取会员认证信息
     *
     * @param openid
     * @return
     */
    MemberAuthDTO getMemberByOpenid(String openid);

    /**
     * 根据手机号获取会员认证信息
     *
     * @param mobile
     * @return
     */
    MemberAuthDTO getMemberByMobile(String mobile);

    /**
     * 新增会员
     *
     * @param member
     * @return
     */
    Long addMember(MemberRegisterDTO member);

    /**
     * 获取登录会员信息
     *
     * @return
     */
    MemberDTO getCurrMemberInfo();

    /**
     * 获取会员地址列表
     *
     * @param memberId
     * @return
     */
    List<MemberAddressDTO> listMemberAddress(Long memberId);


    /**
     * 扣减会员余额
     *
     * @param memberId        会员ID
     * @param deductionAmount 扣减金额
     * @return 是否扣减成功
     */
    boolean deductMemberBalance(Long memberId, Long deductionAmount);

}
