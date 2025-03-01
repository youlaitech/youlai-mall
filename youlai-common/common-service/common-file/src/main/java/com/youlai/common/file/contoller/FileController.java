package com.youlai.common.file.contoller;

import com.youlai.common.file.model.FileInfo;
import com.youlai.common.file.service.OssService;
import com.youlai.common.result.Result;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件控制器
 *
 * @author Ray.Hao
 * @since 1.0.0
 */
@Tag(name = "11.文件接口")
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final OssService ossService;

    @PostMapping
    @Operation(summary = "上传文件")
    public Result<FileInfo> uploadFile(
            @Parameter(
                    name = "file",
                    description = "表单文件对象",
                    required = true,
                    in = ParameterIn.DEFAULT,
                    schema = @Schema(name = "file", format = "binary")
            )
            @RequestParam(value = "file") MultipartFile file
    ) {
        FileInfo fileInfo = ossService.uploadFile(file);
        return Result.success(fileInfo);
    }

    @DeleteMapping
    @Operation(summary = "删除文件")
    public Result deleteFile(
            @Parameter(description = "文件路径") @RequestParam String filePath
    ) {
        boolean result = ossService.deleteFile(filePath);
        return Result.judge(result);
    }

}
