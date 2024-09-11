package com.youlai.codegen.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.youlai.codegen.model.bo.ColumnMetaData;
import com.youlai.codegen.model.bo.TableMetaData;
import com.youlai.codegen.model.query.TablePageQuery;
import com.youlai.codegen.model.vo.TablePageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 数据库访问层
 *
 * @author Ray
 * @since 4.0.0
 */
@Mapper
public interface DatabaseMapper extends BaseMapper {


    Page<TablePageVO> getTablePage(Page<TablePageVO> page, TablePageQuery queryParams);


    List<ColumnMetaData> getTableColumns(String tableName);


    TableMetaData getTableMetadata(String tableName);
}
