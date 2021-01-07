package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.admin.common.AdminConstant;
import com.youlai.admin.pojo.SysDept;
import com.youlai.admin.service.ISysDeptService;
import com.youlai.common.core.enums.QueryModeEnum;
import com.youlai.common.core.result.Result;
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
public class DeptController {

    @Autowired
    private ISysDeptService iSysDeptService;

    @ApiOperation(value = "列表分页", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "部门名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "部门状态", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "queryMode", value = "查询模式", paramType = "query", dataType = "QueryModeEnum")

    })
    @GetMapping
    public Result list(String queryMode,
                       Integer status,
                       String name) {

        LambdaQueryWrapper<SysDept> baseQuery = new LambdaQueryWrapper<SysDept>()
                .orderByAsc(SysDept::getSort)
                .orderByDesc(SysDept::getGmtModified)
                .orderByDesc(SysDept::getGmtCreate);
        QueryModeEnum queryModeEnum = QueryModeEnum.getValue(queryMode);

        List list;
        switch (queryModeEnum){
            case TREE:
                baseQuery = baseQuery.like(StrUtil.isNotBlank(name), SysDept::getName, name)
                        .eq(status != null, SysDept::getStatus, status);
                list = iSysDeptService.listForTree(baseQuery);
                break;
            case TREESELECT:
                list = iSysDeptService.listForTreeSelect(baseQuery);
                break;
            default:
                list = iSysDeptService.list(baseQuery);
                break;
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
        Long parentId = sysDept.getParentId();
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
