package com.youlai.mall.pms.controller.admin;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.enums.QueryModeEnum;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.mall.pms.pojo.bo.admin.ProductBO;
import com.youlai.mall.pms.pojo.domain.PmsSpu;
import com.youlai.mall.pms.service.IPmsSpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

@Api(tags = "【系统管理】商品信息")
@RestController
@RequestMapping("/api.admin/v1/spus")
@Slf4j
@AllArgsConstructor
public class SpuController {

    private IPmsSpuService iPmsSpuService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryMode", value = "查询模式", paramType = "query", dataType = "QueryModeEnum"),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "商品名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "categoryId", value = "商品类目", paramType = "query", dataType = "Long")
    })
    @GetMapping
    public Result list(
            String queryMode,
            Integer page,
            Integer limit,
            String name,
            Long categoryId
    ) {
        QueryModeEnum queryModeEnum = QueryModeEnum.getValue(queryMode);
        switch (queryModeEnum) {
            case PAGE:
                IPage<PmsSpu> result = iPmsSpuService.list(
                        new Page<>(page, limit),
                        new PmsSpu().setName(name).setCategoryId(categoryId)
                );
                return Result.success(result.getRecords(), result.getTotal());
            default:
                return Result.failed(ResultCode.QUERY_MODE_IS_NULL);
        }
    }

    @ApiOperation(value = "商品详情")
    @ApiImplicitParam(name = "id", value = "商品id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ProductBO spu = iPmsSpuService.getBySpuId(id);
        return Result.success(spu);
    }


    @ApiOperation(value = "新增商品")
    @ApiImplicitParam(name = "productBO", value = "实体JSON对象", required = true, paramType = "body", dataType = "ProductBO")
    @PostMapping
    public Result add(@RequestBody ProductBO productBO) {
        boolean status = iPmsSpuService.add(productBO);
        return Result.judge(status);
    }

    @ApiOperation(value = "修改商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "spu", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSpu")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Long id,
            @RequestBody ProductBO spu) {
        boolean status = iPmsSpuService.updateById(spu);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除商品")
    @ApiImplicitParam(name = "ids", value = "id集合,以英文逗号','分隔", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        iPmsSpuService.removeBySpuIds(Arrays.asList(ids.split(",")).stream().map(id -> Long.parseLong(id)).collect(Collectors.toList()));
        return Result.success();
    }

    @ApiOperation(value = "修改商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "spu", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSpu")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Integer id, @RequestBody PmsSpu spu) {
        LambdaUpdateWrapper<PmsSpu> updateWrapper = new LambdaUpdateWrapper<PmsSpu>().eq(PmsSpu::getId, id);
        updateWrapper.set(spu.getStatus() != null, PmsSpu::getStatus, spu.getStatus());
        boolean update = iPmsSpuService.update(updateWrapper);
        return Result.success(update);
    }
}
