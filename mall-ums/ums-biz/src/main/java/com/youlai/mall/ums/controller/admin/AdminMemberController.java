package com.youlai.mall.ums.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.core.constant.SystemConstants;
import com.youlai.common.core.enums.QueryModeEnum;
import com.youlai.common.core.result.Result;
import com.youlai.mall.ums.pojo.UmsMember;
import com.youlai.mall.ums.service.IUmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Api(tags = "会员接口")
@RestController
@RequestMapping("/api.admin/v1/members")
@Slf4j
@AllArgsConstructor
public class AdminMemberController {

    private IUmsMemberService iUmsMemberService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "queryMode", paramType = "query", dataType = "QueryModeEnum"),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "nickname", value = "会员昵称", paramType = "query", dataType = "String")
    })
    @GetMapping
    public Result list(
            String queryMode,
            Integer page,
            Integer limit,
            String nickname
    ) {
        QueryModeEnum queryModeEnum = QueryModeEnum.getValue(queryMode);
        LambdaQueryWrapper<UmsMember> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(UmsMember::getDeleted, SystemConstants.DELETED_VALUE);
        switch (queryModeEnum) {
            default: // PAGE
                queryWrapper.like(StrUtil.isNotBlank(nickname), UmsMember::getNickname, nickname);
                iUmsMemberService.list(new Page<>(page, limit), new UmsMember().setNickname(nickname))
                return Result.success(result.getRecords(), result.getTotal());
        }
    }

    @ApiOperation(value = "获取会员信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result getMemberById(
            @PathVariable Long id
    ) {
        UmsMember member = iUmsMemberService.getById(id);
        return Result.success(member);
    }

    @ApiOperation(value = "修改会员", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "资源id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "member", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsMember")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Integer id,
            @RequestBody UmsMember member) {
        boolean status = iUmsMemberService.updateById(member);
        return Result.status(status);
    }


    @ApiOperation(value = "修改会员【部分更新】", httpMethod = "PATCH")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "member", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsMember")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Long id, @RequestBody UmsMember member) {
        LambdaUpdateWrapper<UmsMember> updateWrapper = new LambdaUpdateWrapper<UmsMember>().eq(UmsMember::getId, id);
        updateWrapper.set(member.getStatus() != null, UmsMember::getStatus, member.getStatus());
        boolean status = iUmsMemberService.update(updateWrapper);
        return Result.status(status);
    }

    @ApiOperation(value = "修改会员积分", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "num", value = "积分数量", required = true, paramType = "query", dataType = "Integer")
    })
    @PutMapping("/{id}/point")
    public Result updatePoint(@PathVariable Long id, @RequestParam Integer num) {
        UmsMember member = iUmsMemberService.getById(id);
        member.setPoint(member.getPoint() + num);
        boolean result = iUmsMemberService.updateById(member);
        try {
            Thread.sleep(15 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.status(result);
    }

    @ApiOperation(value = "删除会员【逻辑删除】", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        boolean status = iUmsMemberService.update(new LambdaUpdateWrapper<UmsMember>()
                .in(UmsMember::getId, Arrays.asList(ids.split(",")))
                .set(UmsMember::getDeleted, SystemConstants.DELETED_VALUE));
        return Result.status(status);
    }
}
