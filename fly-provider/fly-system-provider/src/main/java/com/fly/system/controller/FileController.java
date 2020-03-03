package com.fly.system.controller;

import com.fly.system.utils.FastdfsClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by XianRui on 2020-03-03 17:16
 **/
@RestController
@RequestMapping("/flies")
public class FileController {

    @Autowired
   private FastdfsClientUtils fastdfsClientUtils;


    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
       return fastdfsClientUtils.upload(file);
    }

    /**
     * 文件删除
     * @param fireUrl
     */
    @DeleteMapping("/fileUrl/{fireUrl}")
    public void deleteFile(@PathVariable("fireUrl") String fireUrl){
         fastdfsClientUtils.deleteFile(fireUrl);
    }


}
