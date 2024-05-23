package com.youlai.mall.product.enums;

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

    ASC("ASC","升序"),
    DESC("DESC","降序");

    SortEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private final String value;

    private final String label;

    @Override
    public String toString(){
        return this.value;
    }


}
