package com.fly4j.yshop.common.util;

import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

@NoArgsConstructor
public class ObjectUtils {

    public static boolean isNotNull(Object... objs){
        return !isNull(objs);

    }

    public static boolean isNotEmpty(Object... objs){
        return !isEmpty(objs);
    }


    public static boolean isNull(Object... objs) {
        for (Object obj : objs) {
            if (isEmpty(obj)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        } else if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        } else {
            return obj instanceof Map ? ((Map) obj).isEmpty() : false;
        }

    }

}
