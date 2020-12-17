package com.youlai.mall.pms.search.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字段映射
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldMapping {

    /**
     * 字段类型
     */
    private String type;

    /**
     * 是否索引字段
     */
    private Boolean index;

    /**
     * 格式化样式(data字段)
     */
    private String format;

    /**
     * 分词器
     */
    private String analyzer;

    /**
     * 搜索分词器
     */
    @JsonProperty(value = "search_analyzer")
    private String searchAnalyzer;

    /**
     * 字段长度限制
     */
    @JsonProperty(value = "ignore_above")
    private Integer ignoreAbove;

    /**
     * fields 子字段
     */
    private SubfieldMapping fields;

    public FieldMapping(String type) {
        this.type = type;
    }

    public FieldMapping(String type, Boolean index) {
        this.type = type;
        this.index = index;
    }

    public FieldMapping(String type, Boolean index, String format) {
        this.type = type;
        this.index = index;
        this.format = format;
    }

    public FieldMapping(String type, String analyzer, String searchAnalyzer) {
        this.type = type;
        this.analyzer = analyzer;
        this.searchAnalyzer = searchAnalyzer;
    }

    public FieldMapping(String type, Boolean index, String analyzer, String searchAnalyzer, SubfieldMapping fields) {
        this.type = type;
        this.index = index;
        this.analyzer = analyzer;
        this.searchAnalyzer = searchAnalyzer;
        this.fields = fields;
    }
}

