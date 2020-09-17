package com.fly4j.yshop.ums.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.base.controller.BaseController;
import com.fly4j.yshop.ums.pojo.entity.UmsUser;
import com.fly4j.yshop.ums.service.IUmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "ADMIN-用户管理")
@RestController
@RequestMapping("/users")
@Slf4j
public class UmsUserController extends BaseController {

    @Resource
    private IUmsUserService iUmsUserService;

    @ApiOperation(value = "会员分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, paramType = "path", dataType = "Integer", defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页数量", required = true, paramType = "path", dataType = "Integer", defaultValue = "10"),
            @ApiImplicitParam(name = "nickname", value = "会员昵称", paramType = "query", dataType = "String"),
    })
    @GetMapping("/page/{page}/limit/{limit}")
    public R<Page<UmsUser>> page(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            String name
    ) {
        Page<UmsUser> data = (Page<UmsUser>) iUmsUserService.page(new Page<>(page, limit),
                new LambdaQueryWrapper<UmsUser>()
                        .eq(StrUtil.isNotBlank(name), UmsUser::getNickname, name)
                        .orderByDesc(UmsUser::getCreate_time));
        return R.ok(data);
    }

    @ApiOperation(value = "会员列表", httpMethod = "GET")
    @GetMapping()
    public R list() {
        List<UmsUser> list = iUmsUserService.list();
        return R.ok(list);
    }

    @ApiOperation(value = "新增会员", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "umsMember", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsMember")
    })
    @PostMapping
    public R save(@RequestBody UmsUser umsUser) {
        boolean status = iUmsUserService.save(umsUser);
        return status ? R.ok(null) : R.failed("新增失败");
    }

    @ApiOperation(value = "会员详情", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员id", required = true, paramType = "path", dataType = "Integer"),
    })
    @GetMapping("/{id}")
    public R get(@PathVariable Integer id) {
        UmsUser user = iUmsUserService.getById(id);
        return R.ok(user);
    }

    @ApiOperation(value = "修改会员", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "umsMember", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsMember")
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable("id") Integer id, @RequestBody UmsUser umsUser) {
        boolean status = iUmsUserService.updateById(umsUser);
        return status ? R.ok(null) : R.failed("更新失败");
    }

    @ApiOperation(value = "删除会员", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "会员id", required = true, paramType = "query", allowMultiple = true, dataType = "Integer")
    @DeleteMapping()
    public R delete(@RequestParam("ids") List<Long> ids) {
        boolean status = iUmsUserService.removeByIds(ids);
        return status ? R.ok(null) : R.failed("删除失败");
    }

    @ApiOperation(value = "修改会员状态", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "status", value = "显示状态", required = true, paramType = "path", dataType = "Integer")
    })
    @PutMapping("/id/{id}/status/{status}")
    public R updateStatus(@PathVariable Integer id, @PathVariable Integer status) {
        boolean result = iUmsUserService.update(new LambdaUpdateWrapper<UmsUser>()
                .eq(UmsUser::getId, id)
                .set(UmsUser::getStatus, status));
        if (result) {
            return R.ok("更新成功");
        } else {
            return R.failed("更新失败");
        }
    }
}