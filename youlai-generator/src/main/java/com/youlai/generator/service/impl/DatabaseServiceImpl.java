package com.youlai.generator.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.generator.config.GeneratorProperties;
import com.youlai.generator.mapper.DatabaseMapper;
import com.youlai.generator.model.query.TablePageQuery;
import com.youlai.generator.model.vo.TablePageVO;
import com.youlai.generator.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据源服务实现类
 *
 * @author haoxr
 * @since 4.0.0
 */
@Service
@RequiredArgsConstructor
public class DatabaseServiceImpl implements DatabaseService {


    private final DatabaseMapper databaseMapper;
    private final GeneratorProperties generatorProperties;


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
