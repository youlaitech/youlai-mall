package com.fly4j.yshop.dfs.client;

import com.fly4j.yshop.dfs.pojo.dto.FileInfo;
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
public class FastDfsClient {

    @Value("${file.server.host}")
    private String fileServerHost;

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
        String originalFilename = file.getOriginalFilename();
        String fileExtName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        log.info(">>> begin upload file: originalFilename is {},ext is {}", originalFilename, fileExtName);
        StorePath storePath = this.fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), fileExtName, null);
        String url = getResAccessUrl(storePath);
        return new FileInfo().setUrl(url);
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


    /**
     * 文件HTTP访问URL
     *
     * @param storePath
     * @return
     */
    public String getResAccessUrl(StorePath storePath) {
        return fileServerHost + storePath.getFullPath();
    }

    /**
     * 上传图片
     * @param file
     * @return
     * @throws IOException
     */
    public FileInfo uploadImage(MultipartFile file) throws IOException {
        return uploadImage(file, false);
    }

    /**
     * 上传图片带缩略图
     * @param file
     * @param isThumb 是否上传缩略图
     * @return
     * @throws IOException
     */
    public FileInfo uploadImage(MultipartFile file, Boolean isThumb) throws IOException {
        if (isThumb) {
            String originalFilename = file.getOriginalFilename(); //文件名
            String fileExtName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);   // 文件扩展名
            log.info(">>> begin upload file: originalFilename is {},ext is {}", originalFilename, fileExtName);
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), fileExtName, null);
            String filePath = getResAccessUrl(storePath);
            String thumbImagePath = fileServerHost + thumbImageConfig.getThumbImagePath(storePath.getFullPath());
            return new FileInfo().setUrl(filePath);
        } else {
            return this.upload(file);
        }
    }
}
