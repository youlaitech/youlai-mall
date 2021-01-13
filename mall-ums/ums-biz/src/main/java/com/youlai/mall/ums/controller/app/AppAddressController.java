package com.youlai.mall.ums.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.common.core.result.Result;
import com.youlai.common.web.util.WebUtils;
import com.youlai.mall.ums.pojo.UmsAddress;
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

@Api(tags = "会员接口")
@RestController
@RequestMapping("/api.app/v1/addresses")
@Slf4j
@AllArgsConstructor
public class AppAddressController {

    private IUmsAddressService iUmsAddressService;


    @ApiOperation(value = "获取当前登录会员的地址列表", httpMethod = "GET")
    @GetMapping
    public Result list() {
        Long userId = WebUtils.getUserId();
        List<UmsAddress> addressList = iUmsAddressService.list(new LambdaQueryWrapper<UmsAddress>()
                .eq(UmsAddress::getUserId, userId)
                .orderByDesc(UmsAddress::getDefaulted));
        return Result.success(addressList);
    }


    @ApiOperation(value = "新增地址", httpMethod = "POST")
    @ApiImplicitParam(name = "address", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsAddress")
    @PostMapping
    public Result add(@RequestBody UmsAddress address) {
        Long userId = WebUtils.getUserId();
        address.setUserId(userId);
        if (address.getDefaulted().equals(1)) { // 修改其他默认地址为非默认
            iUmsAddressService.update(new LambdaUpdateWrapper<UmsAddress>()
                    .eq(UmsAddress::getUserId, userId)
                    .eq(UmsAddress::getDefaulted, 1)
                    .set(UmsAddress::getDefaulted, 0)
            );
        }
        boolean status = iUmsAddressService.save(address);
        return Result.status(status);
    }


    @ApiOperation(value = "修改地址", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "部门id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "address", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsAddress")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody UmsAddress address) {
        Long userId = WebUtils.getUserId();
        if (address.getDefaulted().equals(1)) { // 修改其他默认地址为非默认
            iUmsAddressService.update(new LambdaUpdateWrapper<UmsAddress>()
                    .eq(UmsAddress::getUserId, userId)
                    .eq(UmsAddress::getDefaulted, 1)
                    .set(UmsAddress::getDefaulted, 0)
            );
        }
        boolean status = iUmsAddressService.updateById(address);
        return Result.status(status);
    }

    @ApiOperation(value = "删除地址", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id集合字符串，英文逗号分隔", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        boolean status = iUmsAddressService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.status(status);
    }


    @ApiOperation(value = "修改地址【部分更新】", httpMethod = "PATCH")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "address", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsAddress")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Long id, @RequestBody UmsAddress address) {
        Long userId = WebUtils.getUserId();
        LambdaUpdateWrapper<UmsAddress> updateWrapper = new LambdaUpdateWrapper<UmsAddress>()
                .eq(UmsAddress::getUserId, userId);
        if (address.getDefaulted() != null) {
            updateWrapper.set(UmsAddress::getDefaulted, address.getDefaulted());

            if (address.getDefaulted().equals(1)) { // 修改其他默认地址为非默认
                iUmsAddressService.update(new LambdaUpdateWrapper<UmsAddress>()
                        .eq(UmsAddress::getUserId, userId)
                        .eq(UmsAddress::getDefaulted, 1)
                        .set(UmsAddress::getDefaulted, 0)
                );
            }
        }
        boolean status = iUmsAddressService.update(updateWrapper);
        return Result.status(status);
    }
}
