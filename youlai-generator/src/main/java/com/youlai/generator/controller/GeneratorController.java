package com.youlai.generator.controller;

import com.youlai.common.result.Result;
import com.youlai.generator.config.GeneratorProperties;
import com.youlai.generator.model.form.GenConfigForm;
import com.youlai.generator.model.vo.CodePreviewVO;
import com.youlai.generator.service.GenConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 代码生成器控制层
 *
 * @author Ray
 * @since 2.10.0
 */
@Tag(name = "09.代码生成")
@RestController
@RequestMapping("/api/v1/generator")
@RequiredArgsConstructor
public class GeneratorController {

    private final GenConfigService genConfigService;
    private final GeneratorProperties generatorProperties;


    @Operation(summary = "获取代码生成配置")
    @GetMapping("/{tableName}/config")
    public Result<GenConfigForm> getGenConfigFormData(
            @Parameter(description = "表名", example = "sys_user") @PathVariable String tableName
    ) {
        GenConfigForm formData = genConfigService.getGenConfigFormData(tableName);
        return Result.success(formData);
    }

    @Operation(summary = "保存代码生成配置")
    @PostMapping("/{tableName}/config")
    public Result<?> saveGenConfig(@RequestBody GenConfigForm formData) {
        genConfigService.saveGenConfig(formData);
        return Result.success();
    }

    @Operation(summary = "删除代码生成配置")
    @DeleteMapping("/{tableName}/config")
    public Result<?> deleteGenConfig(
            @Parameter(description = "表名", example = "sys_user") @PathVariable String tableName
    ) {
        genConfigService.deleteGenConfig(tableName);
        return Result.success();
    }

    @Operation(summary = "获取预览生成代码")
    @GetMapping("/{tableName}/preview")
    public Result<List<CodePreviewVO>> getCodePreviewData(@PathVariable String tableName) {
        List<CodePreviewVO> list = genConfigService.getCodePreviewData(tableName);
        return Result.success(list);
    }

    @Operation(summary = "下载代码")
    @GetMapping("/{tableName}/download")
    public void downloadZip(HttpServletResponse response, @PathVariable String tableName) throws IOException {
        String[] tableNames = tableName.split(",");
        byte[] data = genConfigService.downloadCode(tableNames);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(generatorProperties.getDownloadFileName(), StandardCharsets.UTF_8));
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setContentType("application/octet-stream; charset=UTF-8");
        response.setDateHeader("Expires", 0);
        //IOUtils.write(data, response.getOutputStream());
    }
}
