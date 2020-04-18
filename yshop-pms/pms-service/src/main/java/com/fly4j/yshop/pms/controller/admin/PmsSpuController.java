package com.fly4j.yshop.pms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.dto.PmsSpuDTO;
import com.fly4j.yshop.pms.pojo.entity.PmsSpu;
import com.fly4j.yshop.pms.service.IPmsSpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "商品接口")
@RestController
@RequestMapping("/spus")
@Slf4j
public class PmsSpuController extends BaseController {

    @Resource
    private IPmsSpuService iPmsSpuService;

    @ApiOperation(value = "商品分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "Integer", defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "path", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "商品名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "is_new", value = "每页数量", paramType = "query", dataType = "Integer"),
    })
    @GetMapping("/page/{page}/limit/{limit}")
    public R<Page<PmsSpu>> page(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            String name,
            String is_new
    ) {
        Page<PmsSpu> data = (Page<PmsSpu>) iPmsSpuService.page(new Page<>(page, limit),
                new LambdaQueryWrapper<PmsSpu>()
                        .like(StrUtil.isNotBlank(name), PmsSpu::getName, name)
                        .eq(is_new != null, PmsSpu::getIs_new, is_new)
                        .orderByDesc(PmsSpu::getCreate_time));
        return R.ok(data);
    }

    @ApiOperation(value = "获取商品列表", httpMethod = "GET")
    @GetMapping()
    public R list() {
        List<PmsSpu> list = iPmsSpuService.list();
        return R.ok(list);
    }


    @ApiOperation(value = "新增商品", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pmsSpuDTO", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSpuDTO")
    })
    @PostMapping
    public R save(@RequestBody PmsSpuDTO pmsSpuDTO) {
        boolean status = iPmsSpuService.add(pmsSpuDTO);
        return status ? R.ok(null) : R.failed("上架商品失败");
    }

    @ApiOperation(value = "商品详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long"),
    })
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        PmsSpu user = iPmsSpuService.getById(id);
        return R.ok(user);
    }


    @ApiOperation(value = "修改商品", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "pmsSpu", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsSpu")
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody PmsSpu pmsSpu) {
        boolean status = iPmsSpuService.updateById(pmsSpu);
        return status ? R.ok(null) : R.failed("更新商品失败");
    }


    @ApiOperation(value = "删除商品", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "商品ID", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping()
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iPmsSpuService.removeByIds(ids);
        return status ? R.ok(null) : R.failed("删除失败");
    }

}
