package com.fly.system.controller;

import com.fly.system.utils.FastdfsClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
   private FastdfsClientUtils fastdfsClientUtils;


    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("file") MultipartFile file) throws IOException {
       return fastdfsClientUtils.upload(file);
    }

    /**
     * 文件删除
     * @param fileUrl
     */
    @DeleteMapping("/fileUrl/{fileUrl}")
    public void deleteFile(@PathVariable("fileUrl") String fileUrl){
         fastdfsClientUtils.deleteFile(fileUrl);
    }


}
