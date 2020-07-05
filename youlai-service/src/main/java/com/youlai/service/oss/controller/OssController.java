package com.youlai.service.oss.controller;

import com.youlai.common.result.Result;
import com.youlai.service.oss.strategy.MinioStrategy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author haoxr
 * @date
 **/
@Api
@RestController
@RequestMapping("/files")
@Slf4j
public class OssController {

    @Autowired
    private MinioStrategy minioStrategy;

    @PostMapping
    @ApiOperation(
            value = "文件上传",
            httpMethod = "POST"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", paramType = "form", dataType = "__file"),
    })
    public Result upload(
            MultipartFile file
    )  {
        Assert.notNull(file,"文件不能为空");
        String path =minioStrategy.upload(file);
        return Result.success(path);
    }

    @DeleteMapping()
    @ApiOperation(
            value = "文件删除",
            httpMethod = "DELETE"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "path", value = "文件路径", required = true, paramType = "query"),
    })
    public Result delete(String path) {
        boolean delete = minioStrategy.delete(path);
        return Result.status(delete);
    }

}
