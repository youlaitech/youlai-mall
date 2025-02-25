package com.youlai.mall.member.api;

import com.youlai.common.core.config.FeignDecoderConfig;
import com.youlai.mall.member.dto.CartItemDTO;
import com.youlai.mall.member.dto.MemberAddressDTO;
import com.youlai.mall.member.dto.MemberAuthDTO;
import com.youlai.mall.member.dto.MemberRegisterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员Feign客户端
 *
 * @author Ray.Hao
 * @since 2022/11/29
 */
@FeignClient(name = "mall-member", contextId = "member", configuration = {FeignDecoderConfig.class})
public interface MemberFeignClient {

    /**
     * 新增会员
     *
     * @param registerDto 会员注册信息
     * @return
     */
    @PostMapping("/app-api/v1/members")
    Long registerMember(@RequestBody MemberRegisterDTO registerDto);

    /**
     * 获取会员的 openid
     *
     * @return openid
     */
    @GetMapping("/app-api/v1/members/{memberId}/openid")
    String getMemberOpenId(@PathVariable Long memberId);

    /**
     * 扣减会员余额
     *
     * @param deductionAmount 扣减金额
     */
    @PutMapping("/app-api/v1/members/balances/deduct")
    Boolean deductMemberBalance(@RequestParam Long deductionAmount);

    /**
     * 根据openId获取会员认证信息
     *
     * @param openid 微信公众平台唯一身份标识
     * @return 会员认证信息
     */
    @GetMapping("/app-api/v1/members/openid/{openid}")
    MemberAuthDTO getUserByOpenId(@PathVariable String openid);

    /**
     * 根据手机号获取会员认证信息
     *
     * @param mobile 手机号
     * @return 会员认证信息
     */
    @GetMapping("/app-api/v1/members/mobile/{mobile}")
    MemberAuthDTO getUserByMobile(@PathVariable String mobile);


    /**
     * 获取当前会员的地址列表
     */
    @GetMapping("/app-api/v1/members/addresses")
    List<MemberAddressDTO> getCurrentMemberAddresses();

    /**
     * 获取当前会员购物车商品列表
     */
    @GetMapping("/app-api/v1/cart-items")
    List<CartItemDTO> getCurrentMemberCartItems();

}


