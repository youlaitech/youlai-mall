package com.youlai.codegen.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.codegen.config.CodegenProperties;
import com.youlai.codegen.mapper.DatabaseMapper;
import com.youlai.codegen.model.query.TablePageQuery;
import com.youlai.codegen.model.vo.TablePageVO;
import com.youlai.codegen.service.DatabaseService;
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
    private final CodegenProperties codegenProperties;

    /**
     * 数据表分页列表
     *
     * @param queryParams 查询参数
     * @return 分页结果
     */
    @Override
    @DS("#queryParams.database")
    public Page<TablePageVO> getTablePage(TablePageQuery queryParams) {
        // 设置排除的表
        List<String> excludeTables = codegenProperties.getExcludeTables();
        queryParams.setExcludeTables(excludeTables);

        return databaseMapper.getTablePage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams
        );
    }


}
