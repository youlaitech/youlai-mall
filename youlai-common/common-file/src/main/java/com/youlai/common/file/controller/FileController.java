package com.youlai.common.file.controller;

import com.youlai.common.file.service.FileService;
import com.youlai.common.file.vo.FileInfo;
import com.youlai.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件接口")
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping
    @ApiOperation(value = "文件上传")
    public Result<FileInfo> uploadFile(
            @ApiParam("表单文件对象") @RequestParam(value = "file") MultipartFile file
    ) {
        FileInfo fileInfo = fileService.uploadFile(file);
        return Result.success(fileInfo);
    }

    @DeleteMapping
    @ApiOperation(value = "文件删除")
    @SneakyThrows
    public Result deleteFile(
            @ApiParam("文件路径") @RequestParam String filePath
    ) {
        boolean result = fileService.deleteFile(filePath);
        return Result.judge(result);
    }

}
