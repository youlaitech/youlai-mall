package com.youlai.mall.ums.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.result.Result;
import com.youlai.common.web.util.JwtUtils;
import com.youlai.mall.ums.pojo.entity.UmsAddress;
import com.youlai.mall.ums.service.IUmsAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api(tags = "「移动端」会员地址")
@RestController
@RequestMapping("/app-api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final IUmsAddressService iUmsAddressService;

    @ApiOperation(value = "获取会员地址列表")
    @GetMapping
    public Result<List<UmsAddress>> listAddresses() {
        List<UmsAddress> addressList = iUmsAddressService.list(new LambdaQueryWrapper<UmsAddress>()
                .eq(UmsAddress::getMemberId, JwtUtils.getUserId())
                .orderByDesc(UmsAddress::getDefaulted));
        return Result.success(addressList);
    }
    @ApiOperation(value = "获取地址详情")
    @GetMapping("/{addressId}")
    public Result<UmsAddress> getAddressDetail(
            @ApiParam("会员地址ID") @PathVariable Long addressId
    ) {
        UmsAddress umsAddress = iUmsAddressService.getById(addressId);
        return Result.success(umsAddress);
    }

    @ApiOperation(value = "新增地址")
    @PostMapping
    public Result addAddress(
            @RequestBody @Validated UmsAddress address
    ) {
        boolean result = iUmsAddressService.addAddress(address);
        return Result.judge(result);
    }


    @ApiOperation(value = "修改地址")
    @PutMapping("/{addressId}")
    public Result updateAddress(
            @ApiParam(value = "地址ID") @PathVariable Long addressId,
            @RequestBody @Validated UmsAddress address
    ) {
        boolean result = iUmsAddressService.updateAddress(address);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除地址")
    @DeleteMapping("/{ids}")
    public Result deleteAddress(
            @ApiParam("地址ID，过个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean status = iUmsAddressService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }

}
