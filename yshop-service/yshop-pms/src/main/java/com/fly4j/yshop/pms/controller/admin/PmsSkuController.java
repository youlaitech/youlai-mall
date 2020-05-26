package com.fly4j.yshop.pms.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.yshop.common.api.PageResult;
import com.fly4j.yshop.common.api.Result;
import com.fly4j.yshop.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.dto.admin.PmsSkuDTO;
import com.fly4j.yshop.pms.pojo.entity.PmsSku;
import com.fly4j.yshop.pms.service.IPmsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "ADMIN-商品SKU")
@RestController
@RequestMapping("/sku")
public class PmsSkuController extends BaseController {

    @Resource
    private IPmsSkuService iPmsSkuService;

    @ApiOperation(value = "SKU列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "spu_name", value = "商品名称", paramType = "query", dataType = "String"),
    })
    @GetMapping
    public Result list(
            @RequestParam Integer page,
            @PathVariable Integer limit,
            @RequestParam(required = false) String spu_name) {
        Map<String, Object> params = new HashMap<>();
        params.put("spu_name", spu_name);
        Page<PmsSkuDTO> result = iPmsSkuService.list(params, new Page<>(page, limit));
        return PageResult.ok(result.getRecords(), result.getTotal());
    }

    @ApiOperation(value = "SKU详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        PmsSku sku = iPmsSkuService.getById(id);
        return Result.ok(sku);
    }

    @ApiOperation(value = "新增SKU", httpMethod = "POST")
    @ApiImplicitParam(name = "pmsSku", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSku")
    @PostMapping
    public Result save(@RequestBody PmsSku pmsSku) {
        boolean status = iPmsSkuService.save(pmsSku);
        return Result.status(status);
    }

    @ApiOperation(value = "修改SKU", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "pmsSku", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSku")
    })
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable("id") Long id, @RequestBody PmsSku pmsSku) {
        boolean status = iPmsSkuService.updateById(pmsSku);
        return Result.status(status);
    }

    @ApiOperation(value = "删除SKU", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping()
    public Result delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iPmsSkuService.removeByIds(ids);
        return Result.status(status);
    }

}
