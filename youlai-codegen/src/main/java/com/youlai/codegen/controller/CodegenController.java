package com.youlai.codegen.controller;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.codegen.config.CodegenProperties;
import com.youlai.codegen.model.form.GenConfigForm;
import com.youlai.codegen.model.query.TablePageQuery;
import com.youlai.codegen.model.vo.CodegenPreviewVO;
import com.youlai.codegen.model.vo.TablePageVO;
import com.youlai.codegen.service.GenConfigService;
import com.youlai.codegen.service.CodegenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器控制层
 *
 * @author Ray
 * @since 4.0.0
 */
@Tag(name = "09.代码生成")
@RestController
@RequestMapping("/api/v1/codegen")
@RequiredArgsConstructor
@Slf4j
public class CodegenController {

    private final CodegenService codegenService;
    private final GenConfigService genConfigService;
    private final CodegenProperties codegenProperties;

    private final DataSource dataSource;

    @GetMapping("/datasource/keys")
    @Operation(summary = "获取所有数据源的key")
    public Result<List<String>> getAllDatasourceKeys() {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        return Result.success(new ArrayList<>(ds.getDataSources().keySet()));
    }

    @Operation(summary = "获取数据表分页列表")
    @GetMapping("/tables/page")
    public PageResult<TablePageVO> getTablePage(
            TablePageQuery queryParams
    ) {
        Page<TablePageVO> result = codegenService.getTablePage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "获取代码生成配置")
    @GetMapping("/{tableName}/config")
    public Result<GenConfigForm> getGenConfigFormData(
            @Parameter(description = "表名", example = "sys_user") @PathVariable String tableName,
            @Parameter(description = "数据源", example = "youlai_system") @RequestParam String datasourceKey
    ) {
        GenConfigForm formData = genConfigService.getGenConfigFormData(tableName,datasourceKey);
        return Result.success(formData);
    }

    @Operation(summary = "保存代码生成配置")
    @PostMapping("/config")
    public Result<Void> saveGenConfig(
            @RequestBody GenConfigForm formData
    ) {
        genConfigService.saveGenConfig(formData);
        return Result.success();
    }

    @Operation(summary = "删除代码生成配置")
    @DeleteMapping("/{tableName}/config")
    public Result<Void> deleteGenConfig(
            @Parameter(description = "表名", example = "sys_user") @PathVariable String tableName
    ) {
        genConfigService.deleteGenConfig(tableName);
        return Result.success();
    }

    @Operation(summary = "获取预览生成代码")
    @GetMapping("/{tableName}/preview")
    public Result<List<CodegenPreviewVO>> getCodePreviewData(@PathVariable String tableName) {
        List<CodegenPreviewVO> list = codegenService.getCodePreviewData(tableName);
        return Result.success(list);
    }

    @Operation(summary = "下载代码")
    @GetMapping("/{tableName}/download")
    public void downloadZip(HttpServletResponse response, @PathVariable String tableName) {
        String[] tableNames = tableName.split(",");
        byte[] data = codegenService.downloadCode(tableNames);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(codegenProperties.getDownloadFileName(), StandardCharsets.UTF_8));
        response.setContentType("application/octet-stream; charset=UTF-8");

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            log.error("Error while writing the zip file to response", e);
            throw new RuntimeException("Failed to write the zip file to response", e);
        }
    }

}
