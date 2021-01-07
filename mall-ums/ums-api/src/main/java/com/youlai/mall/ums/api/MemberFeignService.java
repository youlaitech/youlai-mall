package com.youlai.mall.ums.api;

import com.youlai.common.core.result.Result;
import com.youlai.mall.ums.pojo.UmsMember;
import com.youlai.mall.ums.pojo.dto.AuthMemberDTO;
import com.youlai.mall.ums.pojo.dto.MemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("mall-ums")
public interface MemberFeignService {

    @PostMapping("/api.app/v1/members")
    Result add(@RequestBody UmsMember member);

    /**
     * 获取会员信息
     */
    @GetMapping("/api.app/v1/members/{id}")
    Result<MemberDTO> getMemberById(@PathVariable Long id);


    /**
     * 获取认证会员信息
     */
    @GetMapping("/api.app/v1/members/openid/{openid}")
    Result<AuthMemberDTO> getMemberByOpenid(@PathVariable String openid);
}


