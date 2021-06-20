package com.youlai.mall.ums.api;

import com.youlai.common.result.Result;
import com.youlai.mall.ums.pojo.entity.UmsMember;
import com.youlai.mall.ums.pojo.dto.MemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "mall-ums",contextId = "member")
public interface MemberFeignClient {

    @PostMapping("/app-api/v1/members")
    Result<Long > add(@RequestBody UmsMember member);


    @PutMapping("/app-api/v1/members/{id}")
    Result update(@PathVariable Long id,@RequestBody UmsMember member);


    /**
     * 获取会员信息
     */
    @GetMapping("/app-api/v1/members/{id}")
    Result<MemberDTO> getUserById(@PathVariable Long id);


    /**
     * 获取认证会员信息
     */
    @GetMapping("/app-api/v1/members/openid/{openid}")
    Result<UmsMember> getByOpenid(@PathVariable String openid);

    /**
     * 修改会员积分
     */
    @PutMapping("/app-api/v1/members/{id}/points")
    Result updatePoint(@PathVariable Long id, @RequestParam Integer num);

    /**
     * 扣减会员余额
     */
    @PutMapping("/app-api/v1/members/{id}/deduct-balance")
    Result deductBalance(@PathVariable Long id, @RequestParam Long balance);


    /**
     * 获取会员余额
     */
    @GetMapping("/app-api/v1/members/{id}/balance")
    Result<Long> getBalance(@PathVariable Long id);


}


