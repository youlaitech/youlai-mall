package com.youlai.mall.ums.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.ums.dto.MemberAddressDTO;
import com.youlai.mall.ums.dto.MemberAuthDTO;
import com.youlai.mall.ums.dto.MemberRegisterDTO;
import com.youlai.mall.ums.model.dto.MemberDTO;
import com.youlai.mall.ums.model.entity.UmsMember;
import com.youlai.mall.ums.model.query.MemberPageQuery;
import com.youlai.mall.ums.model.vo.MemberPageVO;

import java.util.List;

/**
 * 会员业务接口
 *
 * @author haoxr
 * @since 2022/2/12
 */
public interface UmsMemberService extends IService<UmsMember> {


    /**
     * 会员分页列表
     */
    IPage<MemberPageVO> listPagedMembers(MemberPageQuery pageQuery);


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
