package com.youlai.mall.ums.feign;

import com.youlai.common.core.result.Result;
import com.youlai.mall.ums.dto.MemberDTO;
import com.youlai.mall.ums.entity.UmsMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("youlai-mall-ums")
public interface RemoteUmsMemberService {

    @GetMapping("/members/member/{openid}")
    Result<MemberDTO> loadMemberByOpenid(@PathVariable String openid);

    @PostMapping("/members")
    Result add(@RequestBody UmsMember member);

}


