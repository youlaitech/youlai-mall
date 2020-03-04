package com.fly.system.controller;

import com.fly.common.core.domain.FileInfo;
import com.fly.system.utils.FastdfsClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by XianRui on 2020-03-03 17:16
 **/
@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FastdfsClientUtil fastdfsClientUtil;

    /**
     * 文件上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping
    public FileInfo upload(@RequestParam("file") MultipartFile file) throws IOException {
        return fastdfsClientUtil.upload(file);
    }

    /**
     * 文件删除
     *
     * @param filePath 文件路径
     */
    @DeleteMapping()
    public void deleteFile(@RequestBody String filePath) {
        fastdfsClientUtil.delete(filePath);
    }

}
