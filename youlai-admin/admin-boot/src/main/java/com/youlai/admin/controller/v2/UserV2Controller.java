package com.youlai.admin.controller.v2;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.admin.pojo.entity.SysUser;
import com.youlai.admin.service.ISysPermissionService;
import com.youlai.admin.service.ISysUserRoleService;
import com.youlai.admin.service.ISysUserService;
import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/api/v2/users")
@Slf4j
@RequiredArgsConstructor
public class UserV2Controller {

    private final ISysUserService iSysUserService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "limit", value = "每页数量", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "keywords", value = "关键字", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "mobile", value = "手机号码", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "deptId", value = "部门ID", paramType = "query", dataType = "Long"),
    })
    @GetMapping("pageList")
    public Result list(Integer page, Integer limit, String keywords, String mobile, Integer status, Long deptId) {
        SysUser user = new SysUser();
        user.setKeywords(keywords);
        user.setMobile(mobile);
        user.setStatus(status);
        user.setDeptId(deptId);

        IPage<SysUser> result = iSysUserService.list(new Page<>(page, limit), user);
        return Result.success(result.getRecords(), result.getTotal());
    }


}
