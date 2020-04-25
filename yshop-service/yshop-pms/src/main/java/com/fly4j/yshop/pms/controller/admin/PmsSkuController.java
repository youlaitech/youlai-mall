package com.fly4j.yshop.pms.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

@RestController
@RequestMapping("/skus")
@Slf4j
@Api(tags = "商品SKUAPI")
public class PmsSkuController extends BaseController {

    @Resource
    private IPmsSkuService iPmsSkuService;

    @ApiOperation(value = "商品分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "Integer", defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "path", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "spu_name", value = "商品名称", paramType = "query", dataType = "String"),
    })
    @GetMapping("/page/{page}/limit/{limit}")
    public R<Page<PmsSkuDTO>> page(@PathVariable Integer page,
                                   @PathVariable Integer limit, @RequestParam(required = false)   String spu_name) {

        Map<String,Object> params=new HashMap<>();
        if(StrUtil.isNotBlank(spu_name)){
            params.put("spu_name",spu_name);
        }
        Page<PmsSkuDTO> data =  iPmsSkuService.selectPage(params, new Page<>(page, limit));
        return R.ok(data);
    }

    @GetMapping()
    public R list() {
        List<PmsSku> list = iPmsSkuService.list();
        return R.ok(list);
    }

    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        PmsSku sku = iPmsSkuService.getById(id);
        return R.ok(sku);
    }

    @PostMapping
    public R add(@RequestBody PmsSku pmsSku) {
        boolean status = iPmsSkuService.save(pmsSku);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody PmsSku pmsSku) {
        boolean status = iPmsSkuService.updateById(pmsSku);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @DeleteMapping()
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iPmsSkuService.removeByIds(ids);
        return status ? R.ok(null) : R.failed("删除失败");
    }

}
