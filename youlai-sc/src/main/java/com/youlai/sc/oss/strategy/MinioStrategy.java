package com.youlai.sc.oss.strategy;


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
    public String upload(MultipartFile multipartFile)  {
        try {
            if(!minioClient.bucketExists(this.props.getBucketName())){
                minioClient.makeBucket(this.props.getBucketName());
            }
            InputStream inputStream = multipartFile.getInputStream();

            String fileName = multipartFile.getOriginalFilename();


            PutObjectOptions putObjectOptions=new PutObjectOptions(multipartFile.getSize(),PutObjectOptions.MIN_MULTIPART_SIZE);
            putObjectOptions.setContentType(multipartFile.getContentType());

            minioClient.putObject(this.props.getBucketName(),fileName,inputStream,putObjectOptions);

            return this.props.getEndpoint();


        }catch (Exception e ){

            throw new CustomException("上传失败");
        }

        return null;
    }


    @Override
    public boolean delete(String path) {
        return false;
    }
}
