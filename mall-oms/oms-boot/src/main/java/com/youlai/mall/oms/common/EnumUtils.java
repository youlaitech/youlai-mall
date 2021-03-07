package com.youlai.mall.oms.common;

import com.youlai.common.base.BaseCodeEnum;

/**
 * @author huawei
 * @desc 枚举类工具类
 * @email huawei_code@163.com
 * @date 2021/2/13
 */
public class EnumUtils {

    public static <T extends BaseCodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
