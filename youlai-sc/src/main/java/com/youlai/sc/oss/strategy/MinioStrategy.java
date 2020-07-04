package com.youlai.sc.oss.strategy;


import com.sun.deploy.util.URLUtil;
import com.youlai.common.exception.CustomException;
import com.youlai.sc.oss.config.OssProperties;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public class MinioStrategy implements OssStrategy {


    private MinioClient minioClient;

    @Setter
    private OssProperties.Minio props;

    public MinioStrategy(){
        minioClient = new MinioClient(
                props.getEndpoint(),
                props.getAccessKey(),
                props.getSecretKey());
    }


    @Override
    public String upload(MultipartFile file)  {
        try {
            if(!minioClient.bucketExists(this.props.getBucketName())){
                minioClient.makeBucket(this.props.getBucketName());
            }
            InputStream inputStream = file.getInputStream();

            String fileName = file.getOriginalFilename();

            PutObjectOptions putObjectOptions=new PutObjectOptions(file.getSize(),PutObjectOptions.MIN_MULTIPART_SIZE);
            putObjectOptions.setContentType(file.getContentType());

            minioClient.putObject(this.props.getBucketName(),fileName,inputStream,putObjectOptions);

            return this.props.getEndpoint() + URLUtil.encodePath(fileName);

        }catch (Exception e ){
            throw new CustomException("上传失败");
        }

    }


    @Override
    public boolean delete(String path) {
        try {
            minioClient.removeObject(this.props.getBucketName(),path);
            return true;
        } catch (Exception e) {
            throw new CustomException("删除失败");
        }
    }
}
