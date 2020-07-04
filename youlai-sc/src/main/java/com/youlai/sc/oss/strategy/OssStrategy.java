package com.youlai.sc.oss.strategy;


import org.springframework.web.multipart.MultipartFile;

public interface OssStrategy {

    String upload(MultipartFile multipartFile) ;
    boolean delete(String path);
}
