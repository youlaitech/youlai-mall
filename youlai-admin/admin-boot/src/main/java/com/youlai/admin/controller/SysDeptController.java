package com.youlai.admin.controller;

import com.youlai.admin.pojo.entity.SysDept;
import com.youlai.admin.pojo.vo.dept.DeptVO;
import com.youlai.admin.service.ISysDeptService;
import com.youlai.common.result.Result;
import com.youlai.common.web.vo.OptionVO;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门控制器
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2020-11-06
 */
@Api(tags = "部门接口")
@RestController
@RequestMapping("/api/v1/depts")
@RequiredArgsConstructor
public class SysDeptController {

    private final ISysDeptService deptService;

    @ApiOperation(value = "部门表格(Table)列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "部门名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "部门状态", paramType = "query", dataType = "Long"),
    })
    @GetMapping("/table")
    public Result listTableDepartments(
            Integer status,
            String name
    ) {
        List<DeptVO> list = deptService.listTableDepartments(status, name);
        return Result.success(list);
    }

    @ApiOperation(value = "部门树形下拉(TreeSelect)列表")
    @GetMapping("/select")
    public Result listTreeSelectDepartments() {
        List<OptionVO> list = deptService.listTreeSelectDepartments();
        return Result.success(list);
    }

    @ApiOperation(value = "部门详情")
    @GetMapping("/{deptId}")
    public Result getDeptDetail(
            @ApiParam("部门ID") @PathVariable Long deptId
    ) {
        SysDept sysDept = deptService.getById(deptId);
        return Result.success(sysDept);
    }

    @ApiOperation(value = "新增部门")
    @PostMapping
    public Result addDept(@RequestBody SysDept dept) {
        Long id = deptService.saveDept(dept);
        return Result.success(id);
    }

    @ApiOperation(value = "修改部门")
    @PutMapping(value = "/{deptId}")
    public Result updateDept(
            @ApiParam("部门ID") @PathVariable Long deptId,
            @RequestBody SysDept dept
    ) {
        deptId = deptService.saveDept(dept);
        return Result.success(deptId);
    }

    @ApiOperation(value = "删除部门")
    @DeleteMapping("/{ids}")
    public Result deleteDepartments(
            @ApiParam("部门ID，多个以英文逗号(,)分割") @PathVariable("ids") String ids
    ) {
        boolean status = deptService.deleteByIds(ids);
        return Result.judge(status);
    }

}
