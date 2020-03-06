package com.fly.flie.utils;

import com.fly.common.core.domain.FileInfo;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
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
public class FastdfsClientUtil {

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
    public FileInfo upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);//文件名
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);   // 文件扩展名
        log.info(">>> begin upload file: originalFilename is {},ext is {}", originalFilename, ext);
        StorePath storePath = this.fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), originalFilename, null);
        String path = storePath.getFullPath();
        // 获取缩略图路径
        String thumbImagePath = thumbImageConfig.getThumbImagePath(path);
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFilePath(fileRootPath + path);
        fileInfo.setThumbImagePath(fileRootPath + thumbImagePath);
        return fileInfo;
    }

    /**
     * 删除文件
     *
     * @Param url 文件路径
     */
    public void deleteFile(String filePath) {
        log.info(">>> begin delete file: filePath is {}", filePath);
        if (StringUtils.isEmpty(filePath)) {
            return;
        }
        StorePath storePath = StorePath.parseFromUrl(filePath);
        fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());

    }
}
