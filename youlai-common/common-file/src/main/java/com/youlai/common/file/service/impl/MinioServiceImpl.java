package com.youlai.common.file.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.youlai.common.file.service.FileService;
import com.youlai.common.file.vo.FileInfo;
import io.minio.*;
import io.minio.http.Method;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;


@Component
@ConfigurationProperties(prefix = "minio")
@Slf4j
public class MinioServiceImpl implements FileService, InitializingBean {

    /**
     * MinIO的API地址
     */
    @Setter
    private String endpoint;

    /**
     * 用户名
     */
    @Setter
    private String accessKey;

    /**
     * 密钥
     */
    @Setter
    private String secretKey;

    /**
     * 存储桶名称
     */
    @Setter
    private String bucketName;

    /**
     * 自定义域名(非必须)
     */
    @Setter
    private String customDomain;


    private MinioClient minioClient;

    @Override
    public void afterPropertiesSet() {
        log.info("MinIO Client init...");
        Assert.notBlank(endpoint, "MinIO endpoint can not be null");
        Assert.notBlank(accessKey, "MinIO accessKey can not be null");
        Assert.notBlank(secretKey, "MinIO secretKey can not be null");
        Assert.notBlank(bucketName, "MinIO bucketName can not be null");
        this.minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }


    /**
     * 上传文件
     *
     * @param file 表单文件对象
     * @return
     */
    @Override
    @SneakyThrows
    public FileInfo uploadFile(MultipartFile file) {
        // 存储桶不存在则创建
        createBucketIfAbsent(bucketName);

        // 生成文件名(日期文件夹)
        String suffix = FileUtil.getSuffix(file.getOriginalFilename());
        String uuid = IdUtil.simpleUUID();
        String fileName = DateUtil.format(LocalDateTime.now(), "yyyy/MM/dd") + "/" + uuid + "." + suffix;

        InputStream inputStream = file.getInputStream();

        // 文件上传
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .contentType(file.getContentType())
                .stream(inputStream, inputStream.available(), -1)
                .build();
        minioClient.putObject(putObjectArgs);

        // 返回文件路径
        String fileUrl;
        if (StrUtil.isBlank(customDomain)) { // 未配置自定义域名
            GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName).object(fileName)
                    .method(Method.GET)
                    .build();

            fileUrl = minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
            fileUrl = fileUrl.substring(0, fileUrl.indexOf("?"));
        } else { // 配置自定义文件路径域名
            fileUrl = customDomain + '/' + bucketName + "/" + fileName;
        }

        FileInfo fileInfo = new FileInfo();
        fileInfo.setName(fileName);
        fileInfo.setUrl(fileUrl);
        return fileInfo;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     *                 https://oss.youlai.tech/default/2022/11/20/test.jpg
     * @return
     */
    @Override
    @SneakyThrows
    public boolean deleteFile(String filePath) {
        Assert.notBlank(filePath, "删除文件路径不能为空");
        String tempStr = "/" + bucketName + "/";
        String fileName = filePath.substring(filePath.indexOf(tempStr) + tempStr.length()); // 2022/11/20/test.jpg

        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .build();
        minioClient.removeObject(removeObjectArgs);
        return true;
    }


    /**
     * PUBLIC桶策略
     * 如果不配置，则新建的存储桶默认是PRIVATE，则存储桶文件会拒绝访问 Access Denied
     *
     * @param bucketName
     * @return
     */
    private static String publicBucketPolicy(String bucketName) {
        /**
         * AWS的S3存储桶策略
         * Principal: 生效用户对象
         * Resource:  指定存储桶
         * Action: 操作行为
         */
        StringBuilder builder = new StringBuilder();
        builder.append("{\"Version\":\"2012-10-17\","
                + "\"Statement\":[{\"Effect\":\"Allow\","
                + "\"Principal\":{\"AWS\":[\"*\"]},"
                + "\"Action\":[\"s3:ListBucketMultipartUploads\",\"s3:GetBucketLocation\",\"s3:ListBucket\"],"
                + "\"Resource\":[\"arn:aws:s3:::" + bucketName + "\"]},"
                + "{\"Effect\":\"Allow\"," + "\"Principal\":{\"AWS\":[\"*\"]},"
                + "\"Action\":[\"s3:ListMultipartUploadParts\",\"s3:PutObject\",\"s3:AbortMultipartUpload\",\"s3:DeleteObject\",\"s3:GetObject\"],"
                + "\"Resource\":[\"arn:aws:s3:::" + bucketName + "/*\"]}]}");

        return builder.toString();
    }

    /**
     * 创建存储桶(存储桶不存在)
     *
     * @param bucketName
     */
    @SneakyThrows
    private void createBucketIfAbsent(String bucketName) {
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
        if (!minioClient.bucketExists(bucketExistsArgs)) {
            MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();

            minioClient.makeBucket(makeBucketArgs);

            // 设置存储桶访问权限为PUBLIC， 如果不配置，则新建的存储桶默认是PRIVATE，则存储桶文件会拒绝访问 Access Denied
            SetBucketPolicyArgs setBucketPolicyArgs = SetBucketPolicyArgs
                    .builder()
                    .bucket(bucketName)
                    .config(publicBucketPolicy(bucketName))
                    .build();
            minioClient.setBucketPolicy(setBucketPolicyArgs);
        }
    }

}
