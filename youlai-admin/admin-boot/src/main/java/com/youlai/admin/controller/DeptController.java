package com.youlai.admin.controller;

import com.youlai.admin.pojo.entity.SysDept;
import com.youlai.admin.pojo.vo.DeptVO;
import com.youlai.admin.pojo.vo.SelectVO;
import com.youlai.admin.pojo.vo.TreeSelectVO;
import com.youlai.admin.service.ISysDeptService;
import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门控制器
 *
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 * @date 2020-11-06
 */
@Api(tags = "部门接口")
@RestController
@RequestMapping("/api/v1/depts")
@RequiredArgsConstructor
public class DeptController {

    private final ISysDeptService deptService;

    @ApiOperation(value = "部门表格（Table）层级列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "部门名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "部门状态", paramType = "query", dataType = "Long"),
    })
    @GetMapping("/table")
    public Result getTableList(Integer status, String name) {
        List<DeptVO> deptTableList = deptService.listTable(status, name);
        return Result.success(deptTableList);
    }

    @ApiOperation(value = "部门下拉（TreeSelect）层级列表")
    @GetMapping("/select")
    public Result getSelectList() {
        List<TreeSelectVO> deptSelectList = deptService.listTreeSelect();
        return Result.success(deptSelectList);
    }

    @ApiOperation(value = "部门详情")
    @ApiImplicitParam(name = "id", value = "部门id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        SysDept sysDept = deptService.getById(id);
        return Result.success(sysDept);
    }

    @ApiOperation(value = "新增部门")
    @PostMapping
    public Result add(@RequestBody SysDept dept) {
        Long id = deptService.saveDept(dept);
        return Result.success(id);
    }

    @ApiOperation(value = "修改部门")
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable Long id, @RequestBody SysDept dept) {
        dept.setId(id);
        Long deptId = deptService.saveDept(dept);
        return Result.success(deptId);
    }

    @ApiOperation(value = "删除部门")
    @ApiImplicitParam(name = "ids", value = "部门ID，多个以英文逗号,分割拼接", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable("ids") String ids) {
        boolean status= deptService.deleteByIds(ids);
        return Result.judge(status);
    }

}
