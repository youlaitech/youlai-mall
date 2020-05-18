package com.fly4j.yshop.pms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.yshop.common.core.controller.BaseController;
import com.fly4j.yshop.pms.pojo.entity.PmsBrand;
import com.fly4j.yshop.pms.service.IPmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "ADMIN-商品品牌")
@RestController
@RequestMapping("/brands")
@Slf4j
public class PmsBrandController extends BaseController {

    @Resource
    private IPmsBrandService iPmsBrandService;

    @ApiOperation(value = "品牌分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "Integer", defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "path", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "名称", paramType = "query", dataType = "String"),
    })
    @GetMapping("/page/{page}/limit/{limit}")
    public R<Page<PmsBrand>> page(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            String name
    ) {
        Page<PmsBrand> data = (Page<PmsBrand>) iPmsBrandService.page(new Page<>(page, limit),
                new LambdaQueryWrapper<PmsBrand>()
                        .eq(StrUtil.isNotBlank(name), PmsBrand::getName, name)
                        .orderByAsc(PmsBrand::getSort)
                        .orderByDesc(PmsBrand::getCreate_time));
        return R.ok(data);
    }

    @ApiOperation(value = "品牌列表", httpMethod = "GET")
    @GetMapping()
    public R list() {
        List<PmsBrand> list = iPmsBrandService.list();
        return R.ok(list);
    }

    @ApiOperation(value = "新增品牌", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pmsBrand", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsBrand")
    })
    @PostMapping
    public R save(@RequestBody PmsBrand pmsBrand) {
        boolean status = iPmsBrandService.save(pmsBrand);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @ApiOperation(value = "品牌详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌id", required = true, paramType = "path", dataType = "Long"),
    })
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        PmsBrand user = iPmsBrandService.getById(id);
        return R.ok(user);
    }

    @ApiOperation(value = "修改品牌", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "pmsBrand", value = "实体JSON对象", required = true, paramType = "body", dataType = "PmsBrand")
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrand) {
        boolean status = iPmsBrandService.updateById(pmsBrand);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @ApiOperation(value = "删除品牌", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "品牌id", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping()
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iPmsBrandService.removeByIds(ids);
        return status ? R.ok(null) : R.failed("删除失败");
    }

    @ApiOperation(value = "修改品牌", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "status", value = "显示状态", required = true, paramType = "path", dataType = "Integer")
    })
    @PutMapping("/id/{id}/status/{status}")
    public R updateStatus(@PathVariable Integer id, @PathVariable Integer status) {
        boolean result = iPmsBrandService.update(new LambdaUpdateWrapper<PmsBrand>()
                .eq(PmsBrand::getId, id)
                .set(PmsBrand::getStatus, status));
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }


}