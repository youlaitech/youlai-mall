package com.youlai.generator.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.generator.model.query.TablePageQuery;
import com.youlai.generator.model.vo.TablePageVO;

import java.util.List;

/**
 * 数据源服务接口
 *
 * @author Ray
 * @since 4.0.0
 */
public interface DatasourceService {

    /**
     * 获取所有数据源
     *
     * @return
     */
    List<String> getAllDatasourceKeys() ;


    /**
     * 获取数据表分页列表
     *
     * @param queryParams 查询参数
     * @return
     */
    Page<TablePageVO> getTablePage(TablePageQuery queryParams);



}
