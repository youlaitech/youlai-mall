package com.youlai.admin.service.impl;

import cn.hutool.core.lang.Assert;
import io.minio.*;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@ConfigurationProperties(prefix = "minio")
public class MinioService implements InitializingBean {

    @Setter
    private String endpoint;

    @Setter
    private String accessKey;

    @Setter
    private String secretKey;

    private MinioClient client;

    @Override
    public void afterPropertiesSet() {
        Assert.notBlank(endpoint, "MinIO URL 为空");
        Assert.notBlank(accessKey, "MinIO accessKey为空");
        Assert.notBlank(secretKey, "MinIO secretKey为空");
        this.client = new MinioClient.Builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
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
