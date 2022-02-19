package com.youlai.mall.ums.controller.admin;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.ums.pojo.entity.UmsMember;
import com.youlai.mall.ums.service.IUmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@Api(tags = "「系统端」会员管理")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class UmsMemberController {

    private final IUmsMemberService memberService;

    @ApiOperation(value = "会员分页列表")
    @GetMapping
    public PageResult<UmsMember> listMembersWithPage(
            @ApiParam("页码") Long pageNum,
            @ApiParam("每页数量") Long pageSize,
            @ApiParam("会员昵称") String nickName
    ) {
        IPage<UmsMember> result = memberService.list(new Page<>(pageNum, pageSize), nickName);
        return PageResult.success(result);
    }

    @ApiOperation(value = "获取会员详情")
    @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result<UmsMember> getMemberDetail(
            @ApiParam("会员ID") @PathVariable Long id
    ) {
        UmsMember user = memberService.getById(id);
        return Result.success(user);
    }

    @ApiOperation(value = "修改会员")
    @PutMapping(value = "/{id}")
    public <T> Result<T> update(
            @ApiParam("会员ID") @PathVariable Long id,
            @RequestBody UmsMember member
    ) {
        boolean status = memberService.updateById(member);
        return Result.judge(status);
    }

    @ApiOperation(value = "选择性修改会员")
    @PatchMapping("/{id}")
    public <T> Result<T> patch(
            @ApiParam("会员ID") @PathVariable Long id,
            @RequestBody UmsMember member
    ) {
        boolean status = memberService.update(new LambdaUpdateWrapper<UmsMember>()
                .eq(UmsMember::getId, id)
                .set(member.getStatus() != null, UmsMember::getStatus, member.getStatus())
        );
        return Result.judge(status);
    }

    @ApiOperation(value = "删除会员")
    @DeleteMapping("/{ids}")
    public <T> Result<T> delete(
            @ApiParam("会员ID，多个以英文逗号(,)拼接") @PathVariable String ids
    ) {
        boolean status = memberService.update(new LambdaUpdateWrapper<UmsMember>()
                .in(UmsMember::getId, Arrays.asList(ids.split(",")))
                .set(UmsMember::getDeleted, GlobalConstants.STATUS_YES));
        return Result.judge(status);
    }
}
