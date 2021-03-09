package com.youlai.common.base;

import lombok.Data;

/**
 * @description document 是 ES 里的一个 JSON 对象，包括零个或多个field，类比关系数据库的一行记录
 * @author haoxr
 * @createTime 2021/3/9 22:14
 */
@Data
public class BaseDocument {

    /**
     * 数据唯一标识
     */
    private String id;

    /**
     * 索引名称
     */
    private String index;
}
