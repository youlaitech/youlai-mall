
package com.youlai.common.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.reflect.Field;

/**
 * 日期工具类
 *
 * @author Ray.Hao
 * @since 2.4.2
 */
public class DateUtils {

    /**
     * 区间日期格式化为数据库日期格式
     * <p>
     * eg：2021-01-01 → 2021-01-01 00:00:00
     *
     * @param obj                要处理的对象
     * @param startTimeFieldName 起始时间字段名
     * @param endTimeFieldName   结束时间字段名
     */
    public static void toDatabaseFormat(Object obj, String startTimeFieldName, String endTimeFieldName) {
        Field startTimeField = ReflectUtil.getField(obj.getClass(), startTimeFieldName);
        Field endTimeField = ReflectUtil.getField(obj.getClass(), endTimeFieldName);

        if (startTimeField != null) {
            processDateTimeField(obj, startTimeField, startTimeFieldName, "yyyy-MM-dd 00:00:00");
        }

        if (endTimeField != null) {
            processDateTimeField(obj, endTimeField, endTimeFieldName, "yyyy-MM-dd 23:59:59");
        }
    }

    /**
     * 处理日期字段
     *
     * @param obj           要处理的对象
     * @param field         字段
     * @param fieldName     字段名
     * @param targetPattern 目标数据库日期格式
     */
    private static void processDateTimeField(Object obj, Field field, String fieldName, String targetPattern) {
        Object fieldValue = ReflectUtil.getFieldValue(obj, fieldName);
        if (fieldValue != null) {
            // 得到原始的日期格式
            String pattern = field.isAnnotationPresent(DateTimeFormat.class) ? field.getAnnotation(DateTimeFormat.class).pattern() : "yyyy-MM-dd";
            // 转换为日期对象
            DateTime dateTime = DateUtil.parse(StrUtil.toString(fieldValue), pattern);
            // 转换为目标数据库日期格式
            ReflectUtil.setFieldValue(obj, fieldName, dateTime.toString(targetPattern));
        }
    }
}
