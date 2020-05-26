package com.fly4j.yshop.pms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.yshop.common.api.PageResult;
import com.fly4j.yshop.common.api.Result;
import com.fly4j.yshop.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.entity.PmsSpu;
import com.fly4j.yshop.pms.pojo.dto.admin.PmsSpuDTO;
import com.fly4j.yshop.pms.service.IPmsSpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "ADMIN-商品SPU")
@RestController
@RequestMapping("/spu")
@Slf4j
public class PmsSpuController extends BaseController {

    @Resource
    private IPmsSpuService iPmsSpuService;

    @ApiOperation(value = "SPU列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "query", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "商品名称", paramType = "query", dataType = "String")
    })
    @GetMapping
    public Result list(
            @RequestParam Integer page,
            @RequestParam Integer limit,
            String name
    ) {
        LambdaQueryWrapper<PmsSpu> queryWrapper = new LambdaQueryWrapper<PmsSpu>()
                .like(StrUtil.isNotBlank(name), PmsSpu::getName, name)
                .orderByDesc(PmsSpu::getCreate_time);
        Page<PmsSpu> result = iPmsSpuService.page(new Page<>(page, limit), queryWrapper);
        return PageResult.ok(result.getRecords(), result.getTotal());
    }


    @ApiOperation(value = "新增SPU", httpMethod = "POST")
    @ApiImplicitParam(name = "pmsSpuDTO", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSpuDTO")
    @PostMapping
    public Result save(@RequestBody PmsSpuDTO pmsSpuDTO) {
        boolean status = iPmsSpuService.add(pmsSpuDTO);
        return Result.status(status);
    }

    @ApiOperation(value = "SPU详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        PmsSpu spu = iPmsSpuService.getById(id);
        return Result.ok(spu);
    }


    @ApiOperation(value = "修改商品", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "pmsSpu", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSpu")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable("id") Long id,
            @RequestBody PmsSpu pmsSpu
    ) {
        boolean status = iPmsSpuService.updateById(pmsSpu);
        return Result.status(status);
    }

    @ApiOperation(value = "删除SPU", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping()
    public Result delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iPmsSpuService.removeByIds(ids);
        return Result.status(status);
    }
}
