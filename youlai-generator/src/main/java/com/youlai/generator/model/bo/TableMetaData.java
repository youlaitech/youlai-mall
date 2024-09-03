package com.youlai.generator.model.bo;

import lombok.Data;


/**
 * 数据表元数据
 *
 * @author Ray
 * @since 2.10.0
 */
@Data
public class TableMetaData {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;

    /**
     * 排序规则
     */
    private String tableCollation;

    /**
     * 存储引擎
     */
    private String engine;

    /**
     * 字符集
     */
    private String charset;

    /**
     * 创建时间
     */
    private String createTime;

}
