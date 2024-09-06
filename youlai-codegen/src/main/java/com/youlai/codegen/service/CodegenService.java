package com.youlai.codegen.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.codegen.model.query.TablePageQuery;
import com.youlai.codegen.model.vo.CodePreviewVO;
import com.youlai.codegen.model.vo.TablePageVO;

import java.util.List;

/**
 * 代码生成服务接口
 *
 * @author Ray
 * @since 4.0.0
 */
public interface CodegenService {

    /**
     * 获取数据表分页列表
     *
     * @param queryParams 查询参数
     * @return
     */
    Page<TablePageVO> getTablePage(TablePageQuery queryParams);

    /**
     * 获取代码预览数据
     *
     * @param tableName 表名
     * @return 包含代码预览信息的列表
     */
    List<CodePreviewVO> getCodePreviewData(String tableName);

    /**
     * 下载代码
     *
     * @param tableNames 表名
     * @return
     */
    byte[] downloadCode(String[] tableNames);


}
