package com.youlai.admin.controller;

import com.youlai.admin.pojo.form.DeptForm;
import com.youlai.admin.pojo.query.DeptQuery;
import com.youlai.admin.pojo.vo.dept.DeptDetailVO;
import com.youlai.admin.pojo.vo.dept.DeptVO;
import com.youlai.admin.service.SysDeptService;
import com.youlai.common.result.Result;
import com.youlai.common.web.domain.Option;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 部门控制器
 *
 * @author haoxr
 * @date 2020/11/6
 */
@Api(tags = "部门接口")
@RestController
@RequestMapping("/api/v1/depts")
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptService deptService;

    @ApiOperation(value = "获取部门列表")
    @GetMapping
    public Result<List<DeptVO>> listDepts(DeptQuery queryParams) {
        List<DeptVO> list = deptService.listDepts(queryParams);
        return Result.success(list);
    }

    @ApiOperation(value = "获取部门下拉选项")
    @GetMapping("/options")
    public Result<List<Option>> lisetDeptOptions() {
        List<Option> list = deptService.lisetDeptOptions();
        return Result.success(list);
    }

    @ApiOperation(value = "获取部门详情")
    @GetMapping("/{deptId}")
    public Result<DeptDetailVO> getDeptDetail(
            @ApiParam("部门ID") @PathVariable Long deptId
    ) {
        DeptDetailVO deptDetail = deptService.getDeptDetail(deptId);
        return Result.success(deptDetail);
    }

    @ApiOperation(value = "新增部门")
    @PostMapping
    public Result saveDept(
            @Valid @RequestBody DeptForm formData
    ) {
        Long id = deptService.saveDept(formData);
        return Result.success(id);
    }

    @ApiOperation(value = "修改部门")
    @PutMapping(value = "/{deptId}")
    public Result updateDept(
            @PathVariable Long deptId,
            @Valid @RequestBody DeptForm formData
    ) {
        deptId = deptService.updateDept(deptId, formData);
        return Result.success(deptId);
    }

    @ApiOperation(value = "删除部门")
    @DeleteMapping("/{ids}")
    public Result deleteDepartments(
            @ApiParam("部门ID，多个以英文逗号(,)分割") @PathVariable("ids") String ids
    ) {
        boolean result = deptService.deleteByIds(ids);
        return Result.judge(result);
    }

}
