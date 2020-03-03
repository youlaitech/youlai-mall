package com.fly.system.utils;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by XianRui on 2020-03-03 17:45
 **/
@Component
@Slf4j
public class FastdfsClientUtils {


    @Value("${file.root-path}")
    private String fileRootPath;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    /**
     * 上传文件
     *
     * @param file 文件
     * @return
     * @throws Exception
     */
    public String upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);//文件名
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);   // 文件扩展名
        log.info(">>> begin upload file: originalFilename is {originalFilename},ext is {ext}", originalFilename, ext);
        StorePath storePath = this.fastFileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), originalFilename, null);
        String path = storePath.getFullPath();
        // 获取缩略图路径
        String thumbImagePath = thumbImageConfig.getThumbImagePath(path);
        log.info(">>> upload success,file path is {}",path);
        return fileRootPath+path;
    }

    /**
     * 删除文件
     *
     * @Param fileUrl 文件访问地址
     */
    public void deleteFile(String fileUrl) {
        log.info(">>> begin delete file: fileUrl is {}", fileUrl);
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            log.warn(e.getMessage());
        }
    }
}
