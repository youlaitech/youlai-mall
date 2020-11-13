package com.youlai.mall.pms.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.result.PageResult;
import com.youlai.common.core.result.Result;
import com.youlai.mall.pms.bo.PmsSpuBO;
import com.youlai.mall.pms.entity.PmsSpu;
import com.youlai.mall.pms.service.IPmsSpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api(tags = "商品接口")
@RestController
@RequestMapping("/goods")
@Slf4j
@AllArgsConstructor
public class PmsSpuController {

    private IPmsSpuService iPmsSpuService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryMode", value = "查询模式（1-表格列表）", defaultValue = "1", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "商品名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "商品类目", paramType = "query", dataType = "Long")
    })
    @GetMapping
    public Result list(
            @RequestParam(defaultValue = "1") Integer queryMode,
            Integer page,
            Integer limit,
            String name,
            Long categoryId
    ) {
        IPage<PmsSpu> result = iPmsSpuService.list(
                new Page<>(page, limit),
                new PmsSpu().setName(name).setCategoryId(categoryId)
        );
        return PageResult.success(result.getRecords(), result.getTotal());
    }

    @ApiOperation(value = "商品详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "商品id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        PmsSpu spu = iPmsSpuService.getById(id);
        return Result.success(spu);
    }

    @ApiOperation(value = "新增商品", httpMethod = "POST")
    @ApiImplicitParam(name = "spuBO", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSpuBO")
    @PostMapping
    public Result add(@RequestBody PmsSpuBO spuBO) {
        iPmsSpuService.add(spuBO);
        return Result.success();
    }

    @ApiOperation(value = "修改商品", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "spu", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSpu")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Integer id,
            @RequestBody PmsSpu spu) {
        boolean status = iPmsSpuService.updateById(spu);
        return Result.status(status);
    }

    @ApiOperation(value = "删除商品", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping
    public Result delete(Long... ids) {
        //boolean status = iPmsSpuService.removeByIds(Arrays.asList(ids));
        return Result.status(false);
    }

    @ApiOperation(value = "修改商品(部分更新)", httpMethod = "PATCH")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "spu", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSpu")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Integer id, @RequestBody PmsSpu spu) {
        LambdaUpdateWrapper<PmsSpu> updateWrapper = new LambdaUpdateWrapper<PmsSpu>().eq(PmsSpu::getId, id);
        if (spu.getStatus() != null) { // 状态更新
            updateWrapper.set(PmsSpu::getStatus, spu.getStatus());
        }
        boolean update = iPmsSpuService.update(updateWrapper);
        return Result.success(update);
    }
}
