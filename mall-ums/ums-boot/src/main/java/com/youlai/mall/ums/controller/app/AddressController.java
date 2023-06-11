package com.youlai.mall.ums.controller.app;

import com.youlai.common.result.Result;
import com.youlai.mall.ums.dto.MemberAddressDTO;
import com.youlai.mall.ums.model.entity.UmsAddress;
import com.youlai.mall.ums.model.form.AddressForm;
import com.youlai.mall.ums.service.UmsAddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Tag(name = "「移动端」会员地址")
@RestController
@RequestMapping("/app-api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final UmsAddressService addressService;

    @Operation(summary= "获取当前会员地址列表")
    @GetMapping
    public Result<List<MemberAddressDTO>> listCurrentMemberAddresses() {
        List<MemberAddressDTO> addressList = addressService.listCurrentMemberAddresses();
        return Result.success(addressList);
    }

    @Operation(summary= "获取地址详情")
    @GetMapping("/{addressId}")
    public Result<UmsAddress> getAddressDetail(
            @Parameter(name = "地址ID") @PathVariable Long addressId
    ) {
        UmsAddress umsAddress = addressService.getById(addressId);
        return Result.success(umsAddress);
    }

    @Operation(summary= "新增地址")
    @PostMapping
    public Result addAddress(
            @RequestBody @Validated AddressForm addressForm
    ) {
        boolean result = addressService.addAddress(addressForm);
        return Result.judge(result);
    }

    @Operation(summary= "修改地址")
    @PutMapping("/{addressId}")
    public Result updateAddress(
            @Parameter(name = "地址ID") @PathVariable Long addressId,
            @RequestBody @Validated AddressForm addressForm
    ) {
        boolean result = addressService.updateAddress(addressForm);
        return Result.judge(result);
    }

    @Operation(summary= "删除地址")
    @DeleteMapping("/{ids}")
    public Result deleteAddress(
            @Parameter(name = "地址ID，过个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean status = addressService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }

}
