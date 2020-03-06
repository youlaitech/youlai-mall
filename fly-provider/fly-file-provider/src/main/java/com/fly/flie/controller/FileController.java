package com.fly.flie.controller;

import com.fly.common.core.domain.FileInfo;
import com.fly.common.core.domain.Result;
import com.fly.flie.utils.FastdfsClientUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by XianRui on 2020-03-03 17:16
 **/
@Api(tags = "文件接口")
@RestController
@RequestMapping("/files")
@Slf4j
public class FileController {

    @Autowired
    private FastdfsClientUtil fastdfsClientUtil;

    @PostMapping
    @ApiOperation(
            value = "文件上传",
            httpMethod = "POST"
    )
    @ApiResponses({
            @ApiResponse(code = 0, message = "上传成功!"),
            @ApiResponse(code = 500, message = "上传失败!")
    })

    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", paramType = "form", dataType = "__file"),
    })
    public Result<FileInfo> upload(
            MultipartFile file
    ) throws IOException {
        FileInfo fileInfo = fastdfsClientUtil.upload(file);
        return Result.success(fileInfo);
    }

    @DeleteMapping()
    @ApiOperation(
            value = "文件删除",
            httpMethod = "DELETE"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "filePath", value = "文件路径", required = true, paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "删除成功!"),
            @ApiResponse(code = 500, message = "删除失败!")
    })
    public Result deleteFile(String filePath) {
        fastdfsClientUtil.deleteFile(filePath);
        return Result.success();
    }
}
