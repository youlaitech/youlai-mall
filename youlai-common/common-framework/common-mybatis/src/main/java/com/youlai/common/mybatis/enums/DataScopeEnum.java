package com.youlai.common.mybatis.enums;

import com.youlai.common.base.IBaseEnum;
import lombok.Getter;

/**
 * 数据权限枚举
 *
 * @author Ray
 * @since 2022/10/14
 */
@Getter
public enum DataScopeEnum implements IBaseEnum<Integer> {

    /**
     * value 越小，数据权限范围越大
     */
    ALL(0, "所有数据"),
    DEPT_AND_SUB(1, "部门及子部门数据"),
    DEPT(2, "本部门数据"),
    SELF(3, "本人数据");

    private final Integer value;

    private final String label;

    DataScopeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}