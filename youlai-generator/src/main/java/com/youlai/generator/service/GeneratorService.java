package com.youlai.generator.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.generator.model.form.GenConfigForm;
import com.youlai.generator.model.query.TablePageQuery;
import com.youlai.generator.model.vo.GeneratorPreviewVO;
import com.youlai.generator.model.vo.TablePageVO;

import java.util.List;

/**
 * 代码生成配置接口
 *
 * @author Ray
 * @since 2.10.0
 */
public interface GeneratorService {

    /**
     * 获取数据表分页列表
     *
     * @param queryParams 查询参数
     * @return
     */
    Page<TablePageVO> getTablePage(TablePageQuery queryParams);

    /**
     * 获取预览生成代码
     *
     * @param tableName 表名
     * @return
     */
    List<GeneratorPreviewVO> getTablePreviewData(String tableName);

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
