package com.youlai.mall.product.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 排序方式
 *
 * @author Ray Hao
 * @since 2024/5/23
 */
@Getter
public enum SortEnum implements IBaseEnum<String> {

    ASC("asc","升序"),
    DESC("desc","降序");

    SortEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @EnumValue
    private final String value;

    private final String label;

    @Override
    @JsonValue
    public String toString(){
        return this.value;
    }


}
