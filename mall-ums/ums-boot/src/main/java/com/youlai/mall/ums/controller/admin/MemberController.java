package com.youlai.mall.ums.controller.admin;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.mall.ums.model.entity.UmsMember;
import com.youlai.mall.ums.model.query.MemberPageQuery;
import com.youlai.mall.ums.model.vo.MemberPageVO;
import com.youlai.mall.ums.service.UmsMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@Tag(name = "Admin-会员管理")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final UmsMemberService memberService;

    @Operation(summary= "会员分页列表")
    @GetMapping("/page")
    public PageResult<MemberPageVO> listPagedMembers(MemberPageQuery pageQuery) {
        IPage<MemberPageVO> page = memberService.listPagedMembers(pageQuery);
        return PageResult.success(page);
    }

    @Operation(summary= "修改会员")
    @PutMapping(value = "/{memberId}")
    public <T> Result<T> update(
            @Parameter(name = "会员ID") @PathVariable Long memberId,
            @RequestBody UmsMember member
    ) {
        boolean status = memberService.updateById(member);
        return Result.judge(status);
    }

    @Operation(summary= "修改会员状态")
    @PatchMapping("/{memberId}/status")
    public <T> Result<T> updateMemberStatus(
            @Parameter(name = "会员ID") @PathVariable Long memberId,
            @RequestBody UmsMember member
    ) {
        boolean status = memberService.update(
                new LambdaUpdateWrapper<UmsMember>()
                        .eq(UmsMember::getId, memberId)
                        .set(UmsMember::getStatus, member.getStatus())
        );
        return Result.judge(status);
    }

    @Operation(summary= "删除会员")
    @DeleteMapping("/{ids}")
    public <T> Result<T> delete(
            @Parameter(name = "会员ID，多个以英文逗号(,)拼接") @PathVariable String ids
    ) {
        boolean status = memberService.update(new LambdaUpdateWrapper<UmsMember>()
                .in(UmsMember::getId, Arrays.asList(ids.split(",")))
                .set(UmsMember::getDeleted, GlobalConstants.STATUS_YES));
        return Result.judge(status);
    }


}
