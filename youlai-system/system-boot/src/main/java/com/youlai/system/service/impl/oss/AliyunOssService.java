package com.youlai.system.service.impl.oss;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.youlai.system.model.vo.FileInfoVO;
import com.youlai.system.service.OssService;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;

/**
 * 阿里云OSS服务
 *
 * @author haoxr
 * @since 2.3.0
 */
@Service
@ConditionalOnProperty(value = "oss.type", havingValue = "aliyun")
@ConfigurationProperties(prefix = "oss.aliyun")
@RequiredArgsConstructor
@Data
public class AliyunOssService implements OssService {
    /**
     * 服务Endpoint
     */
    private String endpoint;
    /**
     * 访问凭据
     */
    private String accessKeyId;
    /**
     * 凭据密钥
     */
    private String accessKeySecret;
    /**
     * 存储桶名称
     */
    private String bucketName;

    private OSS aliyunOssClient;

    @PostConstruct
    public void init() {
        aliyunOssClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    @Override
    @SneakyThrows
    public FileInfoVO uploadFile(MultipartFile file) {

        // 生成文件名(日期文件夹)
        String suffix = FileUtil.getSuffix(file.getOriginalFilename());
        String uuid = IdUtil.simpleUUID();
        String fileName = DateUtil.format(LocalDateTime.now(), "yyyyMMdd") + "/" + uuid + "." + suffix;
        //  try-with-resource 语法糖自动释放流
        try (InputStream inputStream = file.getInputStream()) {

            // 设置上传文件的元信息，例如Content-Type
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            // 创建PutObjectRequest对象，指定Bucket名称、对象名称和输入流
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, metadata);
            // 上传文件
            aliyunOssClient.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败");
        }
        // 获取文件访问路径
        String fileUrl = "https://" + bucketName + "." + endpoint + "/" + fileName;
        FileInfoVO fileInfo = new FileInfoVO();
        fileInfo.setName(fileName);
        fileInfo.setUrl(fileUrl);
        return fileInfo;
    }

    @Override
    public boolean deleteFile(String filePath) {
        Assert.notBlank(filePath, "删除文件路径不能为空");
        String fileHost = "https://" + bucketName + "." + endpoint; // 文件主机域名
        String fileName = filePath.substring(fileHost.length() + 1); // +1 是/占一个字符，截断左闭右开
        aliyunOssClient.deleteObject(bucketName, fileName);
        return true;
    }
}
