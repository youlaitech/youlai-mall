package com.youlai.mall.ums.api;

import com.youlai.common.result.Result;
import com.youlai.mall.pms.pojo.vo.ProductHistoryVO;
import com.youlai.mall.ums.pojo.dto.MemberAuthDTO;
import com.youlai.mall.ums.pojo.dto.MemberDTO;
import com.youlai.mall.ums.pojo.entity.UmsMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "mall-ums", contextId = "member")
public interface MemberFeignClient {

    @PostMapping("/app-api/v1/members")
    Result<Long> add(@RequestBody UmsMember member);

    @PutMapping("/app-api/v1/members/{id}")
    <T> Result<T> update(@PathVariable Long id, @RequestBody UmsMember member);

    /**
     * 获取会员信息
     */
    @GetMapping("/app-api/v1/members/{id}")
    Result<MemberDTO> getUserById(@PathVariable Long id);

    /**
     * 获取会员信息
     */
    @GetMapping("/app-api/v1/members/detail/{id}")
    Result<UmsMember> getUserEntityById(@PathVariable Long id);


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
    Result<MemberAuthDTO> loadUserByOpenId(@PathVariable String openid);


    /**
     * 根据手机号获取会员认证信息
     *
     * @param mobile
     * @return
     */
    @GetMapping("/app-api/v1/members/mobile/{mobile}")
    Result<MemberAuthDTO> loadUserByMobile(String mobile);


}


