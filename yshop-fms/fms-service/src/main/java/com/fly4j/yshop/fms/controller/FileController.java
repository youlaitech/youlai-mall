package com.fly4j.yshop.fms.controller;

import com.baomidou.mybatisplus.extension.api.R;

import com.fly4j.yshop.fms.client.FastDfsClient;
import com.fly4j.yshop.fms.bean.FileInfo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
@Api(tags = "文件API")
@RestController
@RequestMapping("/files")
@Slf4j
public class FileController {

    @Autowired
    private FastDfsClient fastDfsClient;

    @PostMapping
    @ApiOperation(
            value = "文件上传",
            httpMethod = "POST"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", paramType = "form", dataType = "__file"),
    })
    public R<FileInfo> upload(
            MultipartFile file
    ) throws IOException {
        if (file == null) {
            return R.failed("上传文件为空");
        }
        FileInfo fileInfo = fastDfsClient.upload(file);
        return R.ok(fileInfo);
    }

    @DeleteMapping()
    @ApiOperation(
            value = "文件删除",
            httpMethod = "DELETE"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "文件路径", required = true, paramType = "query"),
    })
    public R deleteFile(String url) {
        if (StringUtils.isBlank(url)) {
            return R.failed("上传文件路径为空");
        }
        fastDfsClient.deleteFile(url);
        return R.ok(null);
    }

    @PostMapping("/image")
    @ApiOperation(
            value = "图片上传",
            httpMethod = "POST"
    )
    @ApiResponses({
            @ApiResponse(code = 0, message = "上传成功!"),
            @ApiResponse(code = 500, message = "上传失败!")
    })

    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", paramType = "form", dataType = "__file"),
    })
    public R<FileInfo> uploadImage(
            MultipartFile file
    ) throws IOException {
        if (file == null) {
            return R.failed("上传图片文件为空");
        }
        FileInfo fileInfo = fastDfsClient.uploadImage(file);
        return R.ok(fileInfo);
    }


    @PostMapping("/imageWithThumb")
    @ApiOperation(
            value = "图片上传(带缩略图)",
            httpMethod = "POST"
    )
    @ApiResponses({
            @ApiResponse(code = 0, message = "上传成功!"),
            @ApiResponse(code = 500, message = "上传失败!")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", paramType = "form", dataType = "__file"),
    })
    public R<FileInfo> uploadImageWithThumb(
            MultipartFile file
    ) throws IOException {
        if (file == null) {
            return R.failed("上传图片文件为空");
        }
        FileInfo fileInfo = fastDfsClient.uploadImage(file, true);
        return R.ok(fileInfo);
    }

}
