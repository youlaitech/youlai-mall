package com.youlai.generator.service.impl;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.generator.config.GeneratorProperties;
import com.youlai.generator.mapper.DatabaseMapper;
import com.youlai.generator.model.query.TablePageQuery;
import com.youlai.generator.model.vo.TablePageVO;
import com.youlai.generator.service.DatasourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据源服务实现类
 *
 * @author haoxr
 * @since 4.0.0
 */
@Service
@RequiredArgsConstructor
public class DatasourceServiceImpl implements DatasourceService {


    private final DataSource dataSource;
    private final DatabaseMapper databaseMapper;
    private final GeneratorProperties generatorProperties;


    /**
     * 获取所有数据源
     *
     * @return 数据源列表
     */

    @Override
    public List<String> getAllDatasourceKeys() {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        return new ArrayList<>(ds.getDataSources().keySet());
    }


    /**
     * 数据表分页列表
     *
     * @param queryParams 查询参数
     * @return 分页结果
     */
    public Page<TablePageVO> getTablePage(TablePageQuery queryParams) {
        Page<TablePageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        // 设置排除的表
        List<String> excludeTables = generatorProperties.getExcludeTables();
        queryParams.setExcludeTables(excludeTables);

        return databaseMapper.getTablePage(page, queryParams);
    }


}
