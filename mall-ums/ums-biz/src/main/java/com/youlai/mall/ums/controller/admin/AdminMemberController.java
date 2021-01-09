package com.youlai.mall.ums.controller.admin;

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

@Api(tags = "会员接口")
@RestController
@RequestMapping("/api.admin/v1/members")
@Slf4j
@AllArgsConstructor
public class AdminMemberController {

    private IUmsMemberService iUmsMemberService;

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
}
