package com.youlai.mall.ums.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.vo.ProductHistoryVO;
import com.youlai.mall.ums.dto.MemberAuthInfoDTO;
import com.youlai.mall.ums.dto.MemberDTO;
import com.youlai.mall.ums.dto.MemberInfoDTO;
import com.youlai.mall.ums.pojo.entity.UmsMember;
import com.youlai.mall.ums.pojo.vo.MemberVO;

import java.util.Set;

/**
 * 会员业务接口
 *
 * @author haoxr
 * @date 2022/2/12
 */
public interface IUmsMemberService extends IService<UmsMember> {

    IPage<UmsMember> list(Page<UmsMember> page, String nickname);

    void addProductViewHistory(ProductHistoryVO product, Long userId);

    Set<ProductHistoryVO> getProductViewHistory(Long userId);

    /**
     * 根据 openid 获取会员认证信息
     *
     * @param openid
     * @return
     */
    MemberAuthInfoDTO getByOpenid(String openid);

    /**
     * 根据手机号获取会员认证信息
     *
     * @param mobile
     * @return
     */
    MemberAuthInfoDTO getByMobile(String mobile);

    /**
     * 新增会员
     *
     * @param member
     * @return
     */
    Long addMember(MemberDTO member);

    /**
     * 获取登录会员信息
     *
     * @return
     */
    MemberVO getCurrentMemberInfo();


    /**
     * 「实验室」修改会员余额
     *
     * @param memberId
     * @param balance  会员余额
     * @return
     */
    boolean updateBalance(Long memberId, Long balance);

    /**
     * 「实验室」扣减账户余额
     *
     * @param memberId
     * @param amount   扣减金额
     * @return
     */
    boolean deductBalance(Long memberId, Long amount);

    /**
     * 「实验室」获取会员信息
     *
     * @param memberId
     * @return
     */
    MemberInfoDTO getMemberInfo(Long memberId);
}
