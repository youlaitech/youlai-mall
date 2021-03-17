package com.youlai.mall.ums.api.app;

import com.youlai.common.result.Result;
import com.youlai.mall.ums.pojo.domain.UmsAddress;
import com.youlai.mall.ums.pojo.domain.UmsMember;
import com.youlai.mall.ums.pojo.dto.AuthMemberDTO;
import com.youlai.mall.ums.pojo.dto.MemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "mall-ums")
public interface UmsAddressFeignService {

    /**
     * 获取地址详情
     */
    @GetMapping("/api.app/v1/addresses/{id}")
    Result<UmsAddress> getById(@PathVariable("id") Long id);


    @GetMapping("/api.app/v1/addresses")
    Result<List<UmsAddress>> list();

}


