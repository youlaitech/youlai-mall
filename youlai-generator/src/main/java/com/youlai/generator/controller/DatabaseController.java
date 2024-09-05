package com.youlai.generator.controller;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.common.result.Result;
import com.youlai.generator.model.query.TablePageQuery;
import com.youlai.generator.model.vo.TablePageVO;
import com.youlai.generator.service.DatabaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库管理控制器
 *
 * @author Ray
 * @since 2.10.0
 */
@Tag(name = "数据库管理接口")
@RestController
@RequestMapping("/api/v1/database")
@RequiredArgsConstructor
public class DatabaseController {

    private final DataSource dataSource;
    private final DatabaseService databaseService;

    @GetMapping("/datasource/keys")
    @Operation(summary = "获取所有数据源的key")
    public Result<List<String>> getAllDatasourceKeys() {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        return Result.success(new ArrayList<>(ds.getDataSources().keySet()));
    }

    @Operation(summary = "获取数据表分页列表")
    @GetMapping("/tables/page")
    public PageResult<TablePageVO> getTablePage(
            TablePageQuery queryParams
    ) {
        Page<TablePageVO> result = databaseService.getTablePage(queryParams);
        return PageResult.success(result);
    }

}
