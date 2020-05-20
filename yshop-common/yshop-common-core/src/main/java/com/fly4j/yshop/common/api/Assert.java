package com.fly4j.yshop.common.api;



import lombok.NoArgsConstructor;
import com.fly4j.yshop.common.exception.ApiException;
import com.fly4j.yshop.common.util.CollectionUtils;
import com.fly4j.yshop.common.util.ObjectUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import java.util.Collection;
import java.util.Map;

@NoArgsConstructor
public class Assert {

    public static void gtZero(Integer num, IErrorCode errorCode) {
        if (num == null || num <= 0) {
            fail(errorCode);
        }

    }

    public static void geZero(Integer num, IErrorCode errorCode) {
        if (num == null || num < 0) {
            fail(errorCode);
        }

    }

    public static void gt(Integer num1, Integer num2, IErrorCode errorCode) {
        if (num1 <= num2) {
            fail(errorCode);
        }

    }

    public static void ge(Integer num1, Integer num2, IErrorCode errorCode) {
        if (num1 < num2) {
            fail(errorCode);
        }

    }

    public static void eq(Object obj1, Object obj2, IErrorCode errorCode) {
        if (!obj1.equals(obj2)) {
            fail(errorCode);
        }

    }

    public static void isTrue(boolean condition, IErrorCode errorCode) {
        if (!condition) {
            fail(errorCode);
        }

    }

    public static void isFalse(boolean condition, IErrorCode errorCode) {
        if (condition) {
            fail(errorCode);
        }

    }

    public static void isNull(IErrorCode errorCode, Object... conditions) {

        if (ObjectUtils.isNotNull(conditions)) {
            fail(errorCode);
        }

    }

    public static void notNull(IErrorCode errorCode, Object... conditions) {
        if (ObjectUtils.isNull(conditions)) {
            fail(errorCode);
        }

    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }

    public static void fail(boolean condition, IErrorCode errorCode) {
        if (condition) {
            fail(errorCode);
        }

    }

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(boolean condition, String message) {
        if (condition) {
            fail(message);
        }

    }

    public static void fail(String message, Object[] args, MessageSource messageSource) {
        throw new ApiException(messageSource.getMessage(message, args, LocaleContextHolder.getLocale()));
    }

    public static void fail(boolean condition, String message, Object[] args, MessageSource messageSource) {
        if (condition) {
            fail(message, args, messageSource);
        }

    }

    public static void fail(String message, MessageSource messageSource) {
        throw new ApiException(messageSource.getMessage(message, (Object[])null, LocaleContextHolder.getLocale()));
    }

    public static void fail(boolean condition, String message, MessageSource messageSource) {
        if (condition) {
            fail(message, messageSource);
        }

    }

    public static void notEmpty(Object[] array, IErrorCode errorCode) {
        if (ObjectUtils.isEmpty(array)) {
            fail(errorCode);
        }

    }

    public static void noNullElements(Object[] array, IErrorCode errorCode) {
        if (array != null) {
            Object[] var2 = array;
            int var3 = array.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object element = var2[var4];
                if (element == null) {
                    fail(errorCode);
                }
            }
        }

    }

    public static void notEmpty(Collection<?> collection, IErrorCode errorCode) {
        if (CollectionUtils.isNotEmpty(collection)) {
            fail(errorCode);
        }

    }

    public static void notEmpty(Map<?, ?> map, IErrorCode errorCode) {
        if (ObjectUtils.isEmpty(map)) {
            fail(errorCode);
        }

    }

    public static void isInstanceOf(Class<?> type, Object obj, IErrorCode errorCode) {
        notNull(errorCode, type);
        if (!type.isInstance(obj)) {
            fail(errorCode);
        }

    }

    public static void isAssignable(Class<?> superType, Class<?> subType, IErrorCode errorCode) {
        notNull(errorCode, superType);
        if (subType == null || !superType.isAssignableFrom(subType)) {
            fail(errorCode);
        }

    }

}
