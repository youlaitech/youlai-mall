package com.youlai.mall.ums.controller.app;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.common.web.util.JwtUtils;
import com.youlai.mall.ums.pojo.domain.UmsMember;
import com.youlai.mall.ums.pojo.dto.MemberDTO;
import com.youlai.mall.ums.service.IUmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "【移动端】会员服务")
@RestController
@RequestMapping("/app-api/v1/members")
@Slf4j
@AllArgsConstructor
public class MemberController {

    private IUmsMemberService iUmsMemberService;

    @ApiOperation(value = "获取会员信息")
    @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result getById(
            @PathVariable Long id
    ) {
        MemberDTO memberDTO = new MemberDTO();
        UmsMember user = iUmsMemberService.getOne(
                new LambdaQueryWrapper<UmsMember>()
                        .select(UmsMember::getId, UmsMember::getNickName, UmsMember::getMobile, UmsMember::getBalance)
                        .eq(UmsMember::getId, id)
        );
        if (user != null) {
            BeanUtil.copyProperties(user, memberDTO);
        }
        return Result.success(memberDTO);
    }

    @ApiOperation(value = "根据openid获取会员信息")
    @ApiImplicitParam(name = "openid", value = "微信身份唯一标识", required = true, paramType = "path", dataType = "String")
    @GetMapping("/openid/{openid}")
    public Result getByOpenid(
            @PathVariable String openid
    ) {
        UmsMember member = iUmsMemberService.getOne(new LambdaQueryWrapper<UmsMember>()
                .eq(UmsMember::getOpenid, openid));
        if (member == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        return Result.success(member);
    }

    @ApiOperation(value = "新增会员")
    @ApiImplicitParam(name = "member", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsMember")
    @PostMapping
    public Result<UmsMember> add(@RequestBody UmsMember member) {
        boolean status = iUmsMemberService.save(member);
        if (status) {
            return Result.success(member);
        } else {
            return Result.failed();
        }
    }

    @ApiOperation(value = "修改会员")
    @ApiImplicitParam(name = "member", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsMember")
    @PutMapping("/{id}")
    public Result add(@PathVariable Long id, @RequestBody UmsMember user) {
        boolean status = iUmsMemberService.updateById(user);
        return Result.judge(status);
    }

    @ApiOperation(value = "获取当前请求的会员信息")
    @GetMapping("/me")
    public Result getMemberInfo() {
        Long userId = JwtUtils.getUserId();
        UmsMember member = iUmsMemberService.getById(userId);
        if (member == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        MemberDTO memberDTO = new MemberDTO();
        BeanUtil.copyProperties(member, memberDTO);
        return Result.success(memberDTO);
    }


    @ApiOperation(value = "修改会员积分")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "num", value = "积分数量", required = true, paramType = "query", dataType = "Integer")
    })
    @PutMapping("/{id}/points")
    public Result updatePoint(@PathVariable Long id, @RequestParam Integer num) {
        UmsMember user = iUmsMemberService.getById(id);
        user.setPoint(user.getPoint() + num);
        boolean result = iUmsMemberService.updateById(user);
        return Result.judge(result);
    }

    @ApiOperation(value = "扣减会员余额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "balance", value = "会员余额", required = true, paramType = "query", dataType = "Long")
    })
    @PutMapping("/{id}/deduct-balance")
    public Result updateBalance(@PathVariable Long id, @RequestParam Long balance) {
        boolean result = iUmsMemberService.update(new LambdaUpdateWrapper<UmsMember>()
                .setSql("balance = balance - " + balance)
                .eq(UmsMember::getId, id)
        );
        return Result.judge(result);
    }

    @ApiOperation(value = "获取会员余额")
    @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}/balance")
    public Result<Long> updateBalance(@PathVariable Long id) {
        Long balance = 0l;
        UmsMember user = iUmsMemberService.getById(id);
        if (user != null) {
            balance = user.getBalance();
        }
        return Result.success(balance);
    }

}
