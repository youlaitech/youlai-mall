package com.youlai.common.base;


import cn.hutool.core.util.ObjectUtil;

import java.util.EnumSet;
import java.util.Objects;

/**
 * 枚举通用接口
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/3/27 12:06
 */
public interface IBaseEnum<T> {

    T getValue();

    String getLabel();

    /**
     *
     * @param value
     * @param clazz
     * @param <E> 枚举
     * @return
     */
    static <E extends Enum<E> & IBaseEnum> E getEnumByValue(Object value, Class<E> clazz) {
        Objects.requireNonNull(value);
        EnumSet<E> allEnums = EnumSet.allOf(clazz); // 获取类型下的所有枚举
        E matchEnum = allEnums.stream()
                .filter(e -> ObjectUtil.equal(e.getValue(), value))
                .findFirst()
                .orElse(null);
        return matchEnum;
    }

    static <E extends Enum<E> & IBaseEnum> String getLabelByValue(Object value, Class<E> clazz) {
        Objects.requireNonNull(value);
        EnumSet<E> allEnums = EnumSet.allOf(clazz); // 获取类型下的所有枚举
        E matchEnum = allEnums.stream()
                .filter(e -> ObjectUtil.equal(e.getValue(), value))
                .findFirst()
                .orElse(null);

        String label = null;
        if (matchEnum != null) {
            label = matchEnum.getLabel();
        }
        return label;
    }


}
