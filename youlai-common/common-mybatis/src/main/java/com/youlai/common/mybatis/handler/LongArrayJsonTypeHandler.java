package com.youlai.common.mybatis.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

/**
 * Long 数组类型转换 json
 * <a href="https://www.jianshu.com/p/ab832f3fe81c">https://www.jianshu.com/p/ab832f3fe81c</a>
 *
 * @author Gadfly
 * @since 2021-06-30 15:26
 */
@Slf4j
@Component
@MappedTypes(value = {Long[].class})
@MappedJdbcTypes(value = {JdbcType.OTHER}, includeNullJdbcType = true)
public class LongArrayJsonTypeHandler extends ArrayObjectJsonTypeHandler<Long> {
    public LongArrayJsonTypeHandler() {
        super(Long[].class);
    }
}
