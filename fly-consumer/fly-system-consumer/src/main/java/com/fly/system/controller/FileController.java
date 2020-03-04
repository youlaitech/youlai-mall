package com.fly.system.controller;

import com.fly.common.core.domain.FileInfo;
import com.fly.common.core.domain.Result;
import com.fly.system.feign.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by XianRui on 2020-03-03 18:34
 **/
@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private IFileService iFileService;

    @PostMapping
    private Result upload(@RequestParam("file") MultipartFile file){
        FileInfo fileInfo = iFileService.upload(file);
        return Result.success(fileInfo);
    }

    @DeleteMapping()
    private Result delete(@RequestBody String filePath){
        iFileService.deleteFile(filePath);
        return Result.success();
    }
}
