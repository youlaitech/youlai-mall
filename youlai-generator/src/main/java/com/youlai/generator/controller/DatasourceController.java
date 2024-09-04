package com.youlai.generator.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.common.result.PageResult;
import com.youlai.generator.model.query.TablePageQuery;
import com.youlai.generator.model.vo.TablePageVO;
import com.youlai.generator.service.DatasourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据源控制器
 *
 * @author Ray
 * @since 2.10.0
 */
@Tag(name = "数据源接口")
@RestController
@RequestMapping("/api/v1/datasources")
@RequiredArgsConstructor
public class DatasourceController {

    private final DatasourceService datasourceService;

    @GetMapping("/keys")
    @Operation(summary = "获取所有数据源的key")
    public List<String> getAllDatasourceKeys() {
        return datasourceService.getAllDatasourceKeys();
    }

    @Operation(summary = "获取数据表分页列表")
    @GetMapping("/tables")
    public PageResult<TablePageVO> getTablePage(
            TablePageQuery queryParams
    ) {
        Page<TablePageVO> result = datasourceService.getTablePage(queryParams);
        return PageResult.success(result);
    }

}
