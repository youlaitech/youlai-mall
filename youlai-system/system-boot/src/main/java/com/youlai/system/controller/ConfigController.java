package com.youlai.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.common.core.annotation.Log;
import com.youlai.common.enums.LogModuleEnum;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.system.model.form.ConfigForm;
import com.youlai.system.model.query.ConfigPageQuery;
import com.youlai.system.model.vo.ConfigVO;
import com.youlai.system.service.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 系统配置前端控制层
 *
 * @author Theo
 * @since 2024-07-30 11:25
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "10.系统配置")
@RequestMapping("/api/v1/config")
public class ConfigController {

    private final ConfigService configService;

    @Operation(summary = "系统配置分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPerm('sys:config:query')")
    @Log( value = "系统配置分页列表",module = LogModuleEnum.SETTING)
    public PageResult<ConfigVO> page(@ParameterObject ConfigPageQuery configPageQuery) {
        IPage<ConfigVO> result = configService.page(configPageQuery);
        return PageResult.success(result);
    }

    @Operation(summary = "新增系统配置")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:config:add')")
    @Log( value = "新增系统配置",module = LogModuleEnum.SETTING)
    public Result<?> save(@RequestBody @Valid ConfigForm configForm) {
        return Result.judge(configService.save(configForm));
    }

    @Operation(summary = "获取系统配置表单数据")
    @GetMapping("/{id}/form")
    public Result<ConfigForm> getConfigForm(
            @Parameter(description = "系统配置ID") @PathVariable Long id
    ) {
        ConfigForm formData = configService.getConfigFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "刷新系统配置缓存")
    @PutMapping("/refresh")
    @PreAuthorize("@ss.hasPerm('sys:config:refresh')")
    @Log( value = "刷新系统配置缓存",module = LogModuleEnum.SETTING)
    public Result<ConfigForm> refreshCache() {
        return Result.judge(configService.refreshCache());
    }

    @Operation(summary = "修改系统配置")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPerm('sys:config:update')")
    @Log( value = "修改系统配置",module = LogModuleEnum.SETTING)
    public Result<?> update(@Valid @PathVariable Long id, @RequestBody ConfigForm configForm) {
        return Result.judge(configService.edit(id, configForm));
    }

    @Operation(summary = "删除系统配置")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPerm('sys:config:delete')")
    @Log( value = "删除系统配置",module = LogModuleEnum.SETTING)
    public Result<?> delete(@PathVariable Long id) {
        return Result.judge(configService.delete(id));
    }

}
