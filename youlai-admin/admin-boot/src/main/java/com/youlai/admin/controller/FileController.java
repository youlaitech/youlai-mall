package com.youlai.admin.controller;

import cn.hutool.core.util.IdUtil;
import com.youlai.admin.service.impl.MinioService;
import com.youlai.common.result.Result;
import com.youlai.common.web.exception.BizException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件接口")
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final MinioService minIOService;

    @PostMapping
    @ApiOperation(value = "文件上传")
    public Result<String> upload(
          @ApiParam("文件")  @RequestParam(value = "file") MultipartFile file,
          @ApiParam("桶名称")  @RequestParam(value = "bucketName", required = false, defaultValue = "default") String bucketName
    ) {
        try {
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String objectName = IdUtil.simpleUUID() + "." + suffix;
            String path = minIOService.putObject(bucketName, objectName, file.getInputStream());
            return Result.success(path);
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        }
    }

    @DeleteMapping
    @ApiOperation(value = "文件删除")
    @SneakyThrows
    public Result removeFile(
            @ApiParam("文件路径") @RequestParam String path) {
        int lastIndex = path.lastIndexOf("/");
        String bucketName = path.substring(path.lastIndexOf("/", lastIndex - 1) + 1, lastIndex);
        String objectName = path.substring(lastIndex + 1);
        minIOService.removeObject(bucketName, objectName);
        return Result.success();
    }

}
