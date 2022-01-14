package com.youlai.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.admin.dto.UserAuthDTO;
import com.youlai.admin.pojo.entity.SysUser;
import com.youlai.admin.pojo.query.UserPageQuery;
import com.youlai.admin.pojo.vo.LoginUserVO;
import com.youlai.admin.pojo.vo.UserDetailVO;
import com.youlai.admin.service.ISysPermissionService;
import com.youlai.admin.service.ISysUserRoleService;
import com.youlai.admin.service.ISysUserService;
import com.youlai.common.result.Result;
import com.youlai.common.web.util.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final ISysUserService iSysUserService;
    private final ISysUserRoleService iSysUserRoleService;
    private final PasswordEncoder passwordEncoder;
    private final ISysPermissionService iSysPermissionService;

    @ApiOperation(value = "用户分页列表")
    @GetMapping
    public Result list(UserPageQuery queryParam) {
        IPage<SysUser> result = iSysUserService.list(queryParam);
        return Result.success(result.getRecords(), result.getTotal());
    }

    @ApiOperation(value = "用户详情")
    @GetMapping("/{userId}")
    public Result<UserDetailVO> getUserDetail(
            @ApiParam(value = "用户ID", example = "1") @PathVariable Long userId
    ) {
        UserDetailVO userDetail = iSysUserService.getUserDetailById(userId);
        return Result.success(userDetail);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping
    public Result addUser(@RequestBody SysUser user) {
        boolean result = iSysUserService.saveUser(user);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改用户")
    @PutMapping(value = "/{userId}")
    public Result updateUser(
            @ApiParam("用户ID") @PathVariable Long userId,
            @RequestBody SysUser user
    ) {
        boolean result = iSysUserService.updateUser(user);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除用户")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        boolean status = iSysUserService.removeByIds(Arrays.asList(ids.split(",")).stream().collect(Collectors.toList()));
        return Result.judge(status);
    }

    @ApiOperation(value = "选择性更新用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Long id, @RequestBody SysUser user) {
        System.out.println(user.getPassword() != null);
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<SysUser>().eq(SysUser::getId, id);
        updateWrapper.set(user.getStatus() != null, SysUser::getStatus, user.getStatus());
        updateWrapper.set(user.getPassword() != null, SysUser::getPassword, user.getPassword() != null ? passwordEncoder.encode(user.getPassword()) : null);
        boolean status = iSysUserService.update(updateWrapper);
        return Result.judge(status);
    }


    /**
     * 提供用于用户登录认证信息
     */
    @ApiOperation(value = "根据用户名获取用户信息")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "path", dataType = "String")
    @GetMapping("/username/{username}")
    public Result<UserAuthDTO> getUserByUsername(@PathVariable String username) {
        UserAuthDTO user = iSysUserService.getByUsername(username);
        return Result.success(user);
    }

    @ApiOperation(value = "获取当前登陆的用户信息")
    @GetMapping("/me")
    public Result<LoginUserVO> getCurrentUser() {
        LoginUserVO loginUserVO = new LoginUserVO();
        // 用户基本信息
        Long userId = JwtUtils.getUserId();
        SysUser user = iSysUserService.getById(userId);
        BeanUtil.copyProperties(user, loginUserVO);
        // 用户角色信息
        List<String> roles = JwtUtils.getRoles();
        loginUserVO.setRoles(roles);
        // 用户按钮权限信息
        List<String> perms = iSysPermissionService.listBtnPermByRoles(roles);
        loginUserVO.setPerms(perms);
        return Result.success(loginUserVO);
    }
}
