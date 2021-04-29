package com.youlai.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.common.enums.PermTypeEnum;
import com.youlai.admin.handler.UserBlockHandler;
import com.youlai.admin.pojo.entity.SysUser;
import com.youlai.admin.pojo.entity.SysUserRole;
import com.youlai.admin.pojo.dto.UserDTO;
import com.youlai.admin.pojo.vo.UserVO;
import com.youlai.admin.service.*;
import com.youlai.common.base.BaseController;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import com.youlai.common.web.util.RequestUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/api.admin/v1/users")
@Slf4j
@AllArgsConstructor
public class UserController extends BaseController {

    private final ISysUserService iSysUserService;
    private final ISysUserRoleService iSysUserRoleService;
    private final PasswordEncoder passwordEncoder;

    private final ISysPermissionService iSysPermissionService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "nickname", value = "用户昵称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机号码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "deptId", value = "部门ID", paramType = "query", dataType = "Long"),
    })
    @GetMapping
    public Result list(
            Integer page,
            Integer limit,
            String nickname,
            String mobile,
            Integer status,
            Long deptId
    ) {

        SysUser user = new SysUser();
        user.setNickname(nickname);
        user.setMobile(mobile);
        user.setStatus(status);
        user.setDeptId(deptId);

        IPage<SysUser> result = iSysUserService.list(new Page<>(page, limit), user);
        return Result.success(result.getRecords(), result.getTotal());
    }

    @ApiOperation(value = "用户详情")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(
            @PathVariable Long id
    ) {
        SysUser user = iSysUserService.getById(id);
        if (user != null) {
            List<Long> roleIds = iSysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>()
                    .eq(SysUserRole::getUserId, user.getId())
                    .select(SysUserRole::getRoleId)
            ).stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
            user.setRoleIds(roleIds);
        }
        return Result.success(user);
    }

    @ApiOperation(value = "新增用户")
    @ApiImplicitParam(name = "user", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysUser")
    @PostMapping
    public Result add(@RequestBody SysUser user) {
        boolean result = iSysUserService.saveUser(user);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysUser")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Integer id,
            @RequestBody SysUser user) {
        boolean result = iSysUserService.updateUser(user);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除用户")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        boolean status = iSysUserService.removeByIds(Arrays.asList(ids.split(",")).stream().map(id -> Long.parseLong(id)).collect(Collectors.toList()));
        return Result.judge(status);
    }

    @ApiOperation(value = "局部更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysUser")
    })
    @PatchMapping(value = "/{id}")
    public Result patch(@PathVariable Long id, @RequestBody SysUser user) {
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<SysUser>().eq(SysUser::getId, id);
        updateWrapper.set(user.getStatus() != null, SysUser::getStatus, user.getStatus());
        updateWrapper.set(user.getPassword() != null, SysUser::getPassword, passwordEncoder.encode(user.getPassword()));
        boolean status = iSysUserService.update(updateWrapper);
        return Result.judge(status);
    }


    /**
     * 提供用于用户登录认证需要的用户信息
     *
     * @param username
     * @return
     */
    @ApiOperation(value = "根据用户名获取用户信息")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "path", dataType = "String")
    @GetMapping("/username/{username}")
    public Result getUserByUsername(@PathVariable String username) {
        SysUser user = iSysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username));

        // 用户不存在，返回自定义异常，让调用端处理后续逻辑
        if (user == null) {
            return Result.failed(ResultCode.USER_NOT_EXIST);
        }

        // Entity->DTO
        UserDTO userDTO = new UserDTO();
        BeanUtil.copyProperties(user, userDTO);

        // 获取用户的角色ID集合
        List<Long> roleIds = iSysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, user.getId())
        ).stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        userDTO.setRoleIds(roleIds);

        return Result.success(userDTO);
    }


    @ApiOperation(value = "获取当前登陆的用户信息")
    @SentinelResource(value = "getCurrentUser",
            blockHandlerClass = UserBlockHandler.class, blockHandler = "handleGetCurrentUserBlock"
    )
    @GetMapping("/me")
    public Result<UserVO> getCurrentUser() {
        log.info("获取当前登陆的用户信息 begin");

        UserVO userVO = new UserVO();

        // 用户基本信息
        Long userId = RequestUtils.getUserId();
        SysUser user = iSysUserService.getById(userId);
        BeanUtil.copyProperties(user, userVO);

        // 用户角色信息
        List<Long> roleIds = RequestUtils.getRoleIds();
        userVO.setRoles(roleIds);

        // 用户按钮权限信息
        List<String> perms = iSysPermissionService.listPermsByRoleIds(roleIds, PermTypeEnum.BUTTON.getValue());
        userVO.setPerms(perms);

        return Result.success(userVO);
    }
}
