package com.youlai.admin.service.impl;

import cn.hutool.core.lang.Assert;
import com.youlai.admin.config.MinioProperties;
import io.minio.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Slf4j
@Component
@EnableConfigurationProperties({MinioProperties.class})
public class MinioService implements InitializingBean {

    private MinioProperties minioProperties;

    public MinioService(MinioProperties minioProperties){
        this.minioProperties=minioProperties;
    }

    private MinioClient client;

    @Override
    public void afterPropertiesSet() {
        Assert.notBlank(minioProperties.getEndpoint(), "MinIO URL 为空");
        Assert.notBlank(minioProperties.getAccessKey(), "MinIO accessKey为空");
        Assert.notBlank(minioProperties.getSecretKey(), "MinIO secretKey为空");
        this.client = new MinioClient.Builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

    @SneakyThrows
    public void createBucketIfAbsent(String bucketName) {
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder()
                .bucket(bucketName)
                .build();
        if (!client.bucketExists(bucketExistsArgs)) {
            MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder()
                    .bucket(bucketName).build();
            client.makeBucket(makeBucketArgs);
        }
    }

    public String putObject(String bucketName, String objectName, InputStream inputStream) throws Exception {
        createBucketIfAbsent(bucketName);
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .contentType(MediaType.ALL_VALUE)
                .stream(inputStream, inputStream.available(), -1)
                .build();
        client.putObject(putObjectArgs);
        String path = client.getObjectUrl(bucketName, objectName);
        return path;
    }

    public void removeObject(String bucketName, String objectName) throws Exception {
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build();
        client.removeObject(removeObjectArgs);
    }
}
