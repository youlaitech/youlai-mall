package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.admin.common.AdminConstant;
import com.youlai.admin.domain.entity.SysDept;
import com.youlai.admin.service.ISysDeptService;
import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Api(tags = "部门接口")
@RestController
@RequestMapping("/depts")
@Slf4j
public class SysDeptController {

    @Autowired
    private ISysDeptService iSysDeptService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "部门名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "部门状态", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "mode", value = "查询模式(mode:1-表格数据 2-树形下拉)", defaultValue = "1", paramType = "query", dataType = "Integer"),
    })
    @GetMapping
    public Result list(@RequestParam(required = false, defaultValue = "1") Integer mode,
                       Integer status,
                       String name) {
        LambdaQueryWrapper<SysDept> baseQuery = new LambdaQueryWrapper<SysDept>()
                .orderByAsc(SysDept::getSort)
                .orderByDesc(SysDept::getUpdateTime)
                .orderByDesc(SysDept::getCreateTime);
        List list;
        if (mode.equals(1)) { // 表格数据
            baseQuery = baseQuery.like(StrUtil.isNotBlank(name), SysDept::getName, name)
                    .eq(status != null, SysDept::getStatus, status);
            list = iSysDeptService.listForTableData(baseQuery);
        } else if (mode.equals(2)) { // tree-select 树形下拉数据
            list = iSysDeptService.listForTreeSelect(baseQuery);
        } else {
            list = iSysDeptService.list(baseQuery);
        }
        return Result.success(list);
    }

    @ApiOperation(value = "部门详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "部门id", required = true, paramType = "path", dataType = "Integer")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        SysDept sysDept = iSysDeptService.getById(id);
        return Result.success(sysDept);
    }

    @ApiOperation(value = "新增部门", httpMethod = "POST")
    @ApiImplicitParam(name = "sysDept", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDept")
    @PostMapping
    public Result add(@RequestBody SysDept sysDept) {
        String treePath = getDeptTreePath(sysDept);
        sysDept.setTreePath(treePath);
        boolean status = iSysDeptService.save(sysDept);
        return Result.status(status);
    }

    @ApiOperation(value = "修改部门", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "部门id", required = true, paramType = "path", dataType = "Integer"),
            @ApiImplicitParam(name = "sysDept", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDept")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Integer id,
            @RequestBody SysDept sysDept) {

        String treePath = getDeptTreePath(sysDept);
        sysDept.setTreePath(treePath);
        boolean status = iSysDeptService.updateById(sysDept);
        return Result.status(status);
    }

    @ApiOperation(value = "删除部门", httpMethod = "DELETE")
    @ApiImplicitParam(name = "ids[]", value = "id集合", required = true, paramType = "query", allowMultiple = true, dataType = "Integer")
    @DeleteMapping
    public Result delete(@RequestParam("ids") List<Integer> ids) {

        // 删除部门以及子部门
        Optional.ofNullable(ids).orElse(new ArrayList<>()).forEach(id ->
                iSysDeptService.remove(new LambdaQueryWrapper<SysDept>().eq(SysDept::getId, id)
                        .or().apply("concat (',',tree_path,',') like concat('%,',{0},',%')", id))
        );
        return Result.success();
    }

    private String getDeptTreePath(SysDept sysDept) {
        Integer parentId = sysDept.getParentId();
        String treePath;
        if (parentId.equals(AdminConstant.ROOT_DEPT_ID)) {
            treePath = String.valueOf(AdminConstant.ROOT_DEPT_ID);
        } else {
            SysDept parentDept = iSysDeptService.getById(parentId);
            treePath = Optional.ofNullable(parentDept).map(dept -> dept.getTreePath() + "," + dept.getId()).orElse(Strings.EMPTY);
        }
        return treePath;
    }

}
