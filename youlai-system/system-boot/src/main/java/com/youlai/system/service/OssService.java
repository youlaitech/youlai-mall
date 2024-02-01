package com.youlai.system.service;

import com.youlai.system.model.vo.FileInfoVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 对象存储服务接口层
 *
 * @author haoxr
 * @since 3.0.0
 */
public interface OssService {

    /**
     * 上传文件
     * @param file 表单文件对象
     * @return 文件信息
     */
    FileInfoVO uploadFile(MultipartFile file);

    /**
     * 删除文件
     *
     * @param filePath 文件完整URL
     * @return 删除结果
     */
    boolean deleteFile(String filePath);


}
