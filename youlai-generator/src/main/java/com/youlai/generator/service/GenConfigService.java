package com.youlai.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.generator.model.entity.GenConfig;
import com.youlai.generator.model.form.GenConfigForm;
import com.youlai.generator.model.vo.CodePreviewVO;

import java.util.List;

/**
 * 代码生成配置接口
 *
 * @author Ray
 * @since 2.10.0
 */
public interface GenConfigService extends IService<GenConfig> {


    /**
     * 代码预览
     * 
     * @param tableName
     * @return
     */
    List<CodePreviewVO> getCodePreviewData(String tableName);

    /**
     * 获取代码生成配置
     *
     * @param tableName 表名
     * @return
     */
    GenConfigForm getGenConfigFormData(String tableName);

    /**
     * 保存代码生成配置
     *
     * @param formData 表单数据
     * @return
     */
    void saveGenConfig(GenConfigForm formData);

    /**
     * 删除代码生成配置
     *
     * @param tableName 表名
     * @return
     */
    void deleteGenConfig(String tableName);

    /**
     * 下载代码
     * @param tableNames 表名
     * @return
     */
    byte[] downloadCode(String[] tableNames);
}
