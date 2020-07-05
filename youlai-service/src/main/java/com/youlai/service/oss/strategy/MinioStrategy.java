package com.youlai.service.oss.strategy;


import com.sun.deploy.util.URLUtil;
import com.youlai.common.exception.CustomException;
import com.youlai.service.oss.config.OssProperties;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
            if (!minioClient.bucketExists(this.props.getBucketName())) {
                minioClient.makeBucket(this.props.getBucketName());
            }
            InputStream inputStream = file.getInputStream();

            String fileName = file.getOriginalFilename();



            minioClient.putObject(this.props.getBucketName(), fileName, inputStream, new PutObjectOptions(inputStream.available(), -1));

            return this.props.getEndpoint() + URLUtil.encodePath(fileName);

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
