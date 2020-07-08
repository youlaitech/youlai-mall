package com.youlai.service.oss.strategy;


import com.youlai.common.web.exception.CustomException;
import com.youlai.service.oss.config.OssProperties;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public class MinioStrategy implements OssStrategy {


    private MinioClient minioClient;

    private OssProperties.Minio props;

    public MinioStrategy(OssProperties.Minio props) {
        this.props = props;
        minioClient = new MinioClient(
                props.getEndpoint(),
                props.getAccessKey(),
                props.getSecretKey()
        );
    }


    @Override
    public String upload(MultipartFile file) {
        try {
            String bucketName = this.props.getBucketName();
            if (!minioClient.bucketExists(bucketName)) {
                minioClient.makeBucket(bucketName);
            }
            InputStream inputStream = file.getInputStream();

            String fileName = file.getOriginalFilename();

            minioClient.putObject(bucketName, fileName, inputStream, new PutObjectOptions(inputStream.available(), -1));

            String fileUrl = minioClient.getObjectUrl(bucketName, fileName);
            return fileUrl;
        } catch (Exception e) {
            throw new CustomException("上传失败");
        }

    }


    @Override
    public boolean delete(String path) {
        try {
            minioClient.removeObject(this.props.getBucketName(), path);
            return true;
        } catch (Exception e) {
            throw new CustomException("删除失败");
        }
    }
}
