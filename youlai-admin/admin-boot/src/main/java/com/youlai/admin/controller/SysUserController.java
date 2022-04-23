package com.youlai.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.admin.dto.AuthUserDTO;
import com.youlai.admin.pojo.entity.SysUser;
import com.youlai.admin.pojo.form.UserForm;
import com.youlai.admin.pojo.form.UserImportForm;
import com.youlai.admin.pojo.query.UserPageQuery;
import com.youlai.admin.pojo.vo.user.LoginUserVO;
import com.youlai.admin.pojo.vo.user.UserDetailVO;
import com.youlai.admin.pojo.vo.user.UserExportVO;
import com.youlai.admin.pojo.vo.user.UserPageVO;
import com.youlai.admin.service.ISysPermissionService;
import com.youlai.admin.service.ISysUserService;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.common.web.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理控制器
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/1/15 10:25
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class SysUserController {

    private final ISysUserService iSysUserService;
    private final ISysPermissionService iSysPermissionService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "用户分页列表")
    @GetMapping("/page")
    public PageResult<UserPageVO> listUsersPage(
            UserPageQuery queryParams
    ) {
        IPage<UserPageVO> result = iSysUserService.listUsersPage(queryParams);
        return PageResult.success(result);
    }

    @ApiOperation(value = "获取用户详情")
    @GetMapping("/{userId}")
    public Result<UserDetailVO> getUserDetail(
            @ApiParam(value = "用户ID") @PathVariable Long userId
    ) {
        UserDetailVO userDetail = iSysUserService.getUserDetail(userId);
        return Result.success(userDetail);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping
    public Result addUser(@RequestBody UserForm userForm) {
        boolean result = iSysUserService.saveUser(userForm);
        return Result.judge(result);
    }

    @ApiOperation(value = "修改用户")
    @PutMapping(value = "/{userId}")
    public Result updateUser(
            @ApiParam("用户ID") @PathVariable Long userId,
            @RequestBody UserForm userForm
    ) {
        boolean result = iSysUserService.updateUser(userId, userForm);
        return Result.judge(result);
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{ids}")
    public Result deleteUsers(
            @ApiParam("用户ID，多个以英文逗号(,)分割") @PathVariable String ids
    ) {
        boolean status = iSysUserService.removeByIds(Arrays.asList(ids.split(",")).stream().collect(Collectors.toList()));
        return Result.judge(status);
    }

    @ApiOperation(value = "选择性修改用户")
    @PatchMapping(value = "/{userId}")
    public Result updateUserPart(
            @ApiParam("用户ID") @PathVariable Long userId,
            @RequestBody SysUser user
    ) {
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getId, userId);
        updateWrapper.set(user.getStatus() != null, SysUser::getStatus, user.getStatus());
        updateWrapper.set(user.getPassword() != null, SysUser::getPassword,
                user.getPassword() != null ? passwordEncoder.encode(user.getPassword())
                        : null);
        boolean status = iSysUserService.update(updateWrapper);
        return Result.judge(status);
    }

    @ApiOperation(value = "根据用户名获取认证信息",notes = "提供用于用户登录认证信息")
    @GetMapping("/username/{username}")
    public Result<AuthUserDTO> getAuthInfoByUsername(
            @ApiParam("用户名") @PathVariable String username) {
        AuthUserDTO user = iSysUserService.getAuthInfoByUsername(username);
        return Result.success(user);
    }

    @ApiOperation(value = "获取当前登陆的用户信息")
    @GetMapping("/me")
    public Result<LoginUserVO> getCurrentUser() {
        LoginUserVO loginUserVO = new LoginUserVO();
        // 用户基本信息
        Long userId = UserUtils.getUserId();
        SysUser user = iSysUserService.getById(userId);
        BeanUtil.copyProperties(user, loginUserVO);
        // 用户角色信息
        List<String> roles = UserUtils.getRoles();
        loginUserVO.setRoles(roles);
        // 用户按钮权限信息
        List<String> perms = iSysPermissionService.listBtnPermByRoles(roles);
        loginUserVO.setPerms(perms);
        return Result.success(loginUserVO);
    }

    @ApiOperation("用户导入模板下载")
    @GetMapping("/template")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        String fileName = "用户导入模板.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

        String fileClassPath = "excel-templates" + File.separator + fileName;
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileClassPath);

        ServletOutputStream outputStream = response.getOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();

        excelWriter.finish();
    }

    @ApiOperation("导入用户")
    @PostMapping("/_import")
    public Result importUsers(UserImportForm userImportForm) throws IOException {
        String msg = iSysUserService.importUsers(userImportForm);
        return Result.success(msg);
    }

    @ApiOperation("导出用户")
    @GetMapping("/_export")
    public void exportUsers(UserPageQuery queryParams, HttpServletResponse response) throws IOException {
        String fileName = "用户列表.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

        List<UserExportVO> exportUserList = iSysUserService.listExportUsers(queryParams);

        EasyExcel.write(response.getOutputStream(), UserExportVO.class)
                .sheet("用户列表")
                .doWrite(exportUserList);
    }
}
