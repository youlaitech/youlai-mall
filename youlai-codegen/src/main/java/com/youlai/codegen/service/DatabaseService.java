package com.youlai.codegen.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.codegen.model.query.TablePageQuery;
import com.youlai.codegen.model.vo.TablePageVO;


/**
 * 数据源服务接口
 *
 * @author Ray
 * @since 4.0.0
 */
public interface DatabaseService {

    /**
     * 获取数据表分页列表
     *
     * @param queryParams 查询参数
     * @return
     */
    Page<TablePageVO> getTablePage(TablePageQuery queryParams);



}
