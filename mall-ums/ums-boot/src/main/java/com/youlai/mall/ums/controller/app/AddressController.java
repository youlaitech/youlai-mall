package com.youlai.mall.ums.controller.app;

import com.youlai.common.result.Result;
import com.youlai.mall.ums.dto.MemberAddressDTO;
import com.youlai.mall.ums.pojo.entity.UmsAddress;
import com.youlai.mall.ums.pojo.form.AddressForm;
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

    private final IUmsAddressService addressService;

    @ApiOperation(value = "获取当前会员地址列表")
    @GetMapping
    public Result<List<MemberAddressDTO>> listCurrentMemberAddresses() {
        List<MemberAddressDTO> addressList = addressService.listCurrentMemberAddresses();
        return Result.success(addressList);
    }

    @ApiOperation(value = "获取地址详情")
    @GetMapping("/{addressId}")
    public Result<UmsAddress> getAddressDetail(
            @ApiParam("地址ID") @PathVariable Long addressId
    ) {
        UmsAddress umsAddress = addressService.getById(addressId);
        return Result.success(umsAddress);
    }

    @ApiOperation(value = "新增地址")
    @PostMapping
    public Result addAddress(
            @RequestBody @Validated AddressForm addressForm
    ) {
        boolean result = addressService.addAddress(addressForm);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改地址")
    @PutMapping("/{addressId}")
    public Result updateAddress(
            @ApiParam(value = "地址ID") @PathVariable Long addressId,
            @RequestBody @Validated AddressForm addressForm
    ) {
        boolean result = addressService.updateAddress(addressForm);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除地址")
    @DeleteMapping("/{ids}")
    public Result deleteAddress(
            @ApiParam("地址ID，过个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean status = addressService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }

}
