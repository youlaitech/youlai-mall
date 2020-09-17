package com.fly4j.yshop.ums.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.youlai.common.core.base.controller.BaseController;
import com.fly4j.yshop.ums.pojo.dto.AppAddressDTO;
import com.fly4j.yshop.ums.pojo.entity.UmsAddress;
import com.fly4j.yshop.ums.service.IUmsAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "APP-用户地址")
@RestController
@RequestMapping("/api.app/v1/addresses")
@Slf4j
public class AppAddressController extends BaseController {
    @Resource
    private IUmsAddressService iUmsAddressService;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @ApiOperation(value = "地址列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", value = "返回结果条数", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "user_id", value = "用户ID", paramType = "query", dataType = "Integer")
    })
    @GetMapping()
    public R<List<AppAddressDTO>> page(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer user_id
    ) {
        List<UmsAddress> list = iUmsAddressService.list(new LambdaQueryWrapper<UmsAddress>()
                .eq(user_id != null, UmsAddress::getUser_id, user_id)
                .orderByDesc(UmsAddress::getCreate_time)
                .last(limit != null, "LIMIT " + limit)
        );
        List<AppAddressDTO> resultList = list.stream().map(item -> dozerBeanMapper.map(item, AppAddressDTO.class)).collect(Collectors.toList());
        return R.ok(resultList);
    }

    @ApiOperation(value = "地址详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "地址id", required = true, paramType = "path", dataType = "Integer"),
    })
    @GetMapping("/{id}")
    public R get(@PathVariable Integer id) {
        UmsAddress address = iUmsAddressService.getById(id);
        return R.ok(address);
    }

    @ApiOperation(value = "新增地址", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "umsAddress", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsAddress")
    })
    @PostMapping
    public R save(@RequestBody UmsAddress umsAddress) {
        boolean status = iUmsAddressService.save(umsAddress);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @ApiOperation(value = "修改地址", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "地址id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "umsAddress", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsAddress")
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Integer id, @RequestBody UmsAddress umsAddress) {
        boolean status = iUmsAddressService.updateById(umsAddress);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @ApiOperation(value = "删除地址", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "地址id", required = true, paramType = "query", allowMultiple = true, dataType = "Integer")
    @DeleteMapping()
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iUmsAddressService.removeByIds(ids);
        return status ? R.ok(null) : R.failed("删除失败");
    }

}
