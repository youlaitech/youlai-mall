package com.youlai.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.admin.common.constant.AdminConstants;
import com.youlai.admin.pojo.entity.SysDept;
import com.youlai.admin.pojo.vo.DeptVO;
import com.youlai.admin.pojo.vo.TreeVO;
import com.youlai.admin.service.ISysDeptService;
import com.youlai.common.enums.QueryModeEnum;
import com.youlai.common.result.Result;
import com.youlai.common.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Api(tags = "部门接口")
@RestController
@RequestMapping("/api.admin/v1/depts")
@Slf4j
public class DeptController {

    @Autowired
    private ISysDeptService iSysDeptService;

    @ApiOperation(value = "列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "部门名称", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "部门状态", paramType = "query", dataType = "Long"),
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

        switch (queryModeEnum) {
            case LIST:
                baseQuery = baseQuery
                        .like(StrUtil.isNotBlank(name), SysDept::getName, name)
                        .eq(status != null, SysDept::getStatus, status);
                List<DeptVO> list = iSysDeptService.listDeptVO(baseQuery);
                return Result.success(list);
            case TREE:
                List<TreeVO> treeList = iSysDeptService.listTreeVO(baseQuery);
                return Result.success(treeList);
            default:
                return Result.failed(ResultCode.QUERY_MODE_IS_NULL);
        }
    }

    @ApiOperation(value = "部门详情")
    @ApiImplicitParam(name = "id", value = "部门id", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        SysDept sysDept = iSysDeptService.getById(id);
        return Result.success(sysDept);
    }

    @ApiOperation(value = "新增部门")
    @ApiImplicitParam(name = "sysDept", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDept")
    @PostMapping
    public Result add(@RequestBody SysDept sysDept) {
        String treePath = getDeptTreePath(sysDept);
        sysDept.setTreePath(treePath);
        boolean status = iSysDeptService.save(sysDept);
        return Result.judge(status);
    }

    @ApiOperation(value = "修改部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "部门id", required = true, paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "sysDept", value = "实体JSON对象", required = true, paramType = "body", dataType = "SysDept")
    })
    @PutMapping(value = "/{id}")
    public Result update(
            @PathVariable Integer id,
            @RequestBody SysDept sysDept) {

        String treePath = getDeptTreePath(sysDept);
        sysDept.setTreePath(treePath);
        boolean status = iSysDeptService.updateById(sysDept);
        return Result.judge(status);
    }

    @ApiOperation(value = "删除部门")
    @ApiImplicitParam(name = "ids", value = "id集合", required = true, paramType = "query", dataType = "String")
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable("ids") String ids) {
        AtomicBoolean result = new AtomicBoolean(true);
        List<String> idList = Arrays.asList(ids.split(","));
        // 删除部门以及子部门
        Optional.ofNullable(idList).orElse(new ArrayList<>()).forEach(id ->
                result.set(iSysDeptService.remove(new LambdaQueryWrapper<SysDept>().eq(SysDept::getId, id)
                        .or().apply("concat (',',tree_path,',') like concat('%,',{0},',%')", id)))
        );
        return Result.judge(result.get());
    }

    private String getDeptTreePath(SysDept sysDept) {
        Long parentId = sysDept.getParentId();
        String treePath;
        if (parentId.equals(AdminConstants.ROOT_DEPT_ID)) {
            treePath = String.valueOf(AdminConstants.ROOT_DEPT_ID);
        } else {
            SysDept parentDept = iSysDeptService.getById(parentId);
            treePath = Optional.ofNullable(parentDept).map(dept -> dept.getTreePath() + "," + dept.getId()).orElse(Strings.EMPTY);
        }
        return treePath;
    }

}
