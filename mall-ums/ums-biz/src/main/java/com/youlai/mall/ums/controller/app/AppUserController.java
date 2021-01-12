package com.youlai.mall.ums.controller.app;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.common.core.result.Result;
import com.youlai.common.core.result.ResultCode;
import com.youlai.common.web.util.WebUtils;
import com.youlai.mall.ums.pojo.UmsUser;
import com.youlai.mall.ums.pojo.dto.AuthMemberDTO;
import com.youlai.mall.ums.pojo.dto.MemberDTO;
import com.youlai.mall.ums.pojo.vo.MemberVO;
import com.youlai.mall.ums.service.IUmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Api(tags = "会员接口")
@RestController
@RequestMapping("/api.app/v1/users")
@Slf4j
@AllArgsConstructor
public class AppUserController {

    private IUmsUserService iUmsUserService;

    @ApiOperation(value = "获取会员信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result getMemberById(
            @PathVariable Long id
    ) {
        MemberDTO memberDTO = new MemberDTO();
        UmsUser user = iUmsUserService.getOne(
                new LambdaQueryWrapper<UmsUser>()
                        .select(UmsUser::getId, UmsUser::getNickname, UmsUser::getMobile)
                        .eq(UmsUser::getId, id)
        );
        if (user != null) {
            BeanUtil.copyProperties(user, memberDTO);
        }
        return Result.success(memberDTO);
    }

    @ApiOperation(value = "根据openid获取会员信息", httpMethod = "GET")
    @ApiImplicitParam(name = "openid", value = "微信身份唯一标识", required = true, paramType = "path", dataType = "String")
    @GetMapping("/openid/{openid}")
    public Result getMemberByOpenid(
            @PathVariable String openid
    ) {
        UmsUser user = iUmsUserService.getOne(new LambdaQueryWrapper<UmsUser>()
                .eq(UmsUser::getOpenid, openid));
        if (user == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        AuthMemberDTO authMemberDTO = new AuthMemberDTO();
        BeanUtil.copyProperties(user, authMemberDTO);
        return Result.success(authMemberDTO);
    }

    @ApiOperation(value = "新增会员", httpMethod = "POST")
    @ApiImplicitParam(name = "member", value = "实体JSON对象", required = true, paramType = "body", dataType = "UmsMember")
    @PostMapping
    public Result add(@RequestBody UmsUser user) {
        boolean status = iUmsUserService.save(user);
        return Result.status(status);
    }

    @ApiOperation(value = "获取当前请求的会员信息", httpMethod = "GET")
    @GetMapping("/me")
    public Result getMemberInfo() {
        Long userId = WebUtils.getUserId();
        UmsUser user = iUmsUserService.getById(userId);
        if (user == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }
        MemberVO memberVO = new MemberVO();
        BeanUtil.copyProperties(user, memberVO);
        return Result.success(memberVO);
    }


    @ApiOperation(value = "修改会员积分", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "num", value = "积分数量", required = true, paramType = "query", dataType = "Integer")
    })
    @PutMapping("/{id}/point")
    public Result updatePoint(@PathVariable Long id, @RequestParam Integer num  ) {
        UmsUser user = iUmsUserService.getById(id);
        user.setPoint(user.getPoint() + num);
        boolean result = iUmsUserService.updateById(user);
        return Result.status(result);
    }
}
