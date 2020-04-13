package com.fly4j.shop.ums.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly4j.common.core.controller.BaseController;
import com.fly4j.shop.ums.service.IUmsMemberService;
import com.fly4j.yshop.ums.pojo.entity.UmsMember;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "会员接口")
@RestController
@RequestMapping("/members")
@Slf4j
public class UmsMemberController extends BaseController {

    @Resource
    private IUmsMemberService iUmsMemberService;

    @ApiOperation(value = "会员分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "Integer", defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "path", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "nickname", value = "会员昵称", paramType = "query", dataType = "String"),
    })
    @GetMapping("/page/{page}/limit/{limit}")
    public R<Page<UmsMember>> page(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            String name
    ) {
        Page<UmsMember> data = (Page<UmsMember>) iUmsMemberService.page(new Page<>(page, limit),
                new LambdaQueryWrapper<UmsMember>()
                        .eq(StrUtil.isNotBlank(name), UmsMember::getNickname, name)
                        .orderByDesc(UmsMember::getCreate_time));
        return R.ok(data);
    }

    @ApiOperation(value = "会员列表", httpMethod = "GET")
    @GetMapping()
    public R list() {
        List<UmsMember> list = iUmsMemberService.list();
        return R.ok(list);
    }

    @ApiOperation(value = "新增会员", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pmsBrand", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsMember")
    })
    @PostMapping
    public R save(@RequestBody UmsMember pmsBrand) {
        boolean status = iUmsMemberService.save(pmsBrand);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @ApiOperation(value = "会员详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员id", required = true, paramType = "path", dataType = "Long"),
    })
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        UmsMember user = iUmsMemberService.getById(id);
        return R.ok(user);
    }

    @ApiOperation(value = "修改会员", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "pmsBrand", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsMember")
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Long id, @RequestBody UmsMember pmsBrand) {
        boolean status = iUmsMemberService.updateById(pmsBrand);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @ApiOperation(value = "删除会员", httpMethod = "delete")
    @ApiImplicitParam(name = "ids", value = "会员id", required = true, paramType = "query", allowMultiple = true, dataType = "Long")
    @DeleteMapping()
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iUmsMemberService.removeByIds(ids);
        return status ? R.ok(null) : R.failed("删除失败");
    }

    @ApiOperation(value = "修改会员状态", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "status", value = "显示状态", required = true, paramType = "path", dataType = "Integer")
    })
    @PutMapping("/id/{id}/status/{status}")
    public R updateStatus(@PathVariable Integer id, @PathVariable Integer status) {
        boolean result = iUmsMemberService.update(new LambdaUpdateWrapper<UmsMember>()
                .eq(UmsMember::getId, id)
                .set(UmsMember::getStatus, status));
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }
}