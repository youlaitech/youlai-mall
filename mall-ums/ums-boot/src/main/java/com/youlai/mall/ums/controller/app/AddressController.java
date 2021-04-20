package com.youlai.mall.ums.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.common.result.Result;
import com.youlai.common.web.util.RequestUtils;
import com.youlai.mall.ums.pojo.domain.UmsAddress;
import com.youlai.mall.ums.service.IUmsAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api(tags = "【移动端】会员地址")
@RestController
@RequestMapping("/api.app/v1/addresses")
@Slf4j
@AllArgsConstructor
public class AddressController {

    private IUmsAddressService iUmsAddressService;

    private final Integer ADDRESS_DEFAULTED = 1;

    @ApiOperation(value = "获取登录会员的地址列表")
    @GetMapping
    public Result list() {
        Long memberId = RequestUtils.getUserId();
        List<UmsAddress> addressList = iUmsAddressService.list(new LambdaQueryWrapper<UmsAddress>()
                .eq(UmsAddress::getMemberId, memberId)
                .orderByDesc(UmsAddress::getDefaulted));
        return Result.success(addressList);
    }


    @ApiOperation(value = "新增地址")
    @ApiImplicitParam(name = "address", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsAddress")
    @PostMapping
    public Result add(@RequestBody UmsAddress address) {
        Long memberId = RequestUtils.getUserId();
        address.setMemberId(memberId);
        if (ADDRESS_DEFAULTED.equals(address.getDefaulted())) { // 修改其他默认地址为非默认
            iUmsAddressService.update(new LambdaUpdateWrapper<UmsAddress>()
                    .eq(UmsAddress::getMemberId, memberId)
                    .eq(UmsAddress::getDefaulted, 1)
                    .set(UmsAddress::getDefaulted, 0)
            );
        }
        boolean status = iUmsAddressService.save(address);
        return Result.judge(status);
    }


    @ApiOperation(value = "修改地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "部门id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "address", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsAddress")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody UmsAddress address) {
        Long memberId = RequestUtils.getUserId();
        if (address.getDefaulted().equals(1)) { // 修改其他默认地址为非默认
            iUmsAddressService.update(new LambdaUpdateWrapper<UmsAddress>()
                    .eq(UmsAddress::getMemberId, memberId)
                    .eq(UmsAddress::getDefaulted, 1)
                    .set(UmsAddress::getDefaulted, 0)
            );
        }
        boolean status = iUmsAddressService.updateById(address);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除地址")
    @ApiImplicitParam(name = "ids", value = "id集合字符串，英文逗号分隔", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        boolean status = iUmsAddressService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }


    @ApiOperation(value = "修改地址【部分更新】")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "address", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsAddress")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Long id, @RequestBody UmsAddress address) {
        Long userId = RequestUtils.getUserId();
        LambdaUpdateWrapper<UmsAddress> updateWrapper = new LambdaUpdateWrapper<UmsAddress>()
                .eq(UmsAddress::getMemberId, userId);
        if (address.getDefaulted() != null) {
            updateWrapper.set(UmsAddress::getDefaulted, address.getDefaulted());

            if (address.getDefaulted().equals(1)) { // 修改其他默认地址为非默认
                iUmsAddressService.update(new LambdaUpdateWrapper<UmsAddress>()
                        .eq(UmsAddress::getMemberId, userId)
                        .eq(UmsAddress::getDefaulted, 1)
                        .set(UmsAddress::getDefaulted, 0)
                );
            }
        }
        boolean status = iUmsAddressService.update(updateWrapper);
        return Result.judge(status);
    }

    @ApiOperation(value = "根据id查询收货地址详情")
    @ApiImplicitParam(name = "id", value = "地址 id", required = true, paramType = "path", dataType = "String")
    @GetMapping("/{id}")
    public Result<UmsAddress> getAddressById(@PathVariable("id") String id) {
        return Result.success(iUmsAddressService.getById(id));
    }
}
