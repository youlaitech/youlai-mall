package com.youlai.mall.pms.search.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 反射工具类
 */
public class ReflectUtils extends cn.hutool.core.util.ReflectUtil {

    /**
     * 根据指定的注解获取标注了注解的字段
     *
     * @param targetClass     目标对象Class
     * @param annotationClass 注解Class
     * @return
     */
    public static List<Field> getClassFieldsByAnnotation(Class<?> targetClass, Class<? extends Annotation> annotationClass) {
        if (Objects.nonNull(targetClass) && Objects.nonNull(annotationClass)) {
            Field[] fields = getFields(targetClass);
            if (Objects.nonNull(fields) && fields.length > 0) {
                List<Field> response = new ArrayList<>();
                for (Field field : fields) {
                    Annotation annotation = field.getAnnotation(annotationClass);
                    if (Objects.nonNull(annotation)) {
                        response.add(field);
                    }
                }
                return response.isEmpty() ? null : response;
            }
        }
        return null;
    }


    /**
     * 根据指定Type获取字段
     *
     * @param targetClass 目标对象Class
     * @param type        类型
     * @return
     */
    public static List<Field> getClassFieldsByType(Class<?> targetClass, Type type) {
        if (Objects.nonNull(targetClass) && Objects.nonNull(type)) {
            Field[] fields = getFields(targetClass);
            if (Objects.nonNull(fields) && fields.length > 0) {
                List<Field> response = new ArrayList<>();
                for (Field field : fields) {
                    if (field.getType().equals(type)) {
                        response.add(field);
                    }
                }
                return response.isEmpty() ? null : response;
            }
        }
        return null;
    }
}

