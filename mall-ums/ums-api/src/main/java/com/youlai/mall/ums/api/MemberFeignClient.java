package com.youlai.mall.ums.api;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.vo.ProductHistoryVO;
import com.youlai.mall.ums.dto.MemberAuthInfoDTO;
import com.youlai.mall.ums.dto.MemberDTO;
import com.youlai.mall.ums.dto.MemberInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "mall-ums", contextId = "member")
public interface MemberFeignClient {

    /**
     * 新增会员
     *
     * @param member
     * @return
     */
    @PostMapping("/app-api/v1/members")
    Result<Long> addMember(@RequestBody MemberDTO member);

    /**
     * 获取会员的 openid
     *
     * @return
     */
    @PostMapping("/app-api/v1/members/{memberId}/openid")
    Result<String> getMemberOpenId(@PathVariable Long memberId);

    /**
     * 扣减会员余额
     */
    @PutMapping("/app-api/v1/members/current/balances/_deduct")
    <T> Result<T> deductBalance(@RequestParam Long balances);

    /**
     * 添加浏览记录
     */
    @PostMapping("/app-api/v1/members/view/history")
    <T> Result<T> addProductViewHistory(@RequestBody ProductHistoryVO product);

    /**
     * 根据openId获取会员认证信息
     *
     * @param openid
     * @return
     */
    @GetMapping("/app-api/v1/members/openid/{openid}")
    Result<MemberAuthInfoDTO> loadUserByOpenId(@PathVariable String openid);

    /**
     * 根据手机号获取会员认证信息
     *
     * @param mobile
     * @return
     */
    @GetMapping("/app-api/v1/members/mobile/{mobile}")
    Result<MemberAuthInfoDTO> loadUserByMobile(@PathVariable String mobile);

    /**
     * 「实验室」修改会员余额
     *
     * @param memberId
     * @param balance  余额(单位:分)
     * @return
     */
    @PutMapping("/api/v1/members/{memberId}/balance")
    Result updateBalance(@PathVariable Long memberId, @RequestParam Integer balance);

    /**
     * 「实验室」扣减会员余额
     *
     * @param memberId
     * @param amount   扣减金额(单位:分)
     * @return
     */
    @PutMapping("/api/v1/members/{memberId}/balance/_deduct")
    Result deductBalance(@PathVariable Long memberId, @RequestParam Long amount);

    /**
     * 「实验室」获取会员信息
     *
     * @param memberId
     * @return
     */
    @GetMapping("/api/v1/members/{memberId}/info")
    Result<MemberInfoDTO> getMemberInfo(@PathVariable Long memberId);

}


