package com.youlai.common.file.controller;

import com.youlai.common.file.service.MinioService;
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

    private final MinioService minioService;

    @PostMapping
    @ApiOperation(value = "文件上传")
    @SneakyThrows
    public Result<String> uploadFile(
            @ApiParam("文件") @RequestParam(value = "file") MultipartFile file,
            @ApiParam("存储桶名称(非必须，微服务有单独默认存储桶)") @RequestParam(value = "bucketName", required = false) String bucketName
    ) {
        String path = minioService.putObject(file, bucketName);
        return Result.success(path);
    }

    @DeleteMapping
    @ApiOperation(value = "文件删除")
    @SneakyThrows
    public Result deleteFile(
            @ApiParam("文件路径") @RequestParam String path) {
        int lastIndex = path.lastIndexOf("/");
        String bucket = path.substring(path.lastIndexOf("/", lastIndex - 1) + 1, lastIndex);
        String fileName = path.substring(lastIndex + 1);
        minioService.removeObject(bucket, fileName);
        return Result.success();
    }

}
