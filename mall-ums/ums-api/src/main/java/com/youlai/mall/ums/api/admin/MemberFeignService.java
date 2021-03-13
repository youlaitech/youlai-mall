package com.youlai.mall.ums.api.admin;

import com.youlai.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "mall-ums",contextId = "AdminMemberFeignService")
public interface MemberFeignService {

    /**
     * 扣减会员余额
     */
    @PatchMapping("/api.admin/v1/users/{id}/balance/_deduct")
    Result deductBalance(@PathVariable Long id, @RequestParam Long amount);

}


