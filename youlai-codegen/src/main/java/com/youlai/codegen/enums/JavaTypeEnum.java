package com.youlai.codegen.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 表单类型枚举
 *
 * @author Ray
 * @since 2.10.0
 */
@Getter
public enum JavaTypeEnum {

    VARCHAR("varchar", "String", "string"),
    CHAR("char", "String", "string"),
    BLOB("blob", "byte[]", "Uint8Array"),
    TEXT("text", "String", "string"),
    JSON("json", "String", "any"),
    INTEGER("int", "Integer", "number"),
    TINYINT("tinyint", "Integer", "number"),
    SMALLINT("smallint", "Integer", "number"),
    MEDIUMINT("mediumint", "Integer", "number"),
    BIGINT("bigint", "Long", "bigint"),
    FLOAT("float", "Float", "number"),
    DOUBLE("double", "Double", "number"),
    DECIMAL("decimal", "BigDecimal", "number"),
    DATE("date", "LocalDate", "Date"),
    DATETIME("datetime", "LocalDateTime", "Date");

    // 数据库类型
    private final String dbType;
    // Java类型
    private final String javaType;
    // TypeScript类型
    private final String tsType;

    // 数据库类型和Java类型的映射
    private static final Map<String, JavaTypeEnum> typeMap = new HashMap<>();

    // 初始化映射关系
    static {
        for (JavaTypeEnum javaTypeEnum : JavaTypeEnum.values()) {
            typeMap.put(javaTypeEnum.getDbType(), javaTypeEnum);
        }
    }

    JavaTypeEnum(String dbType, String javaType, String tsType) {
        this.dbType = dbType;
        this.javaType = javaType;
        this.tsType = tsType;
    }

    /**
     * 根据数据库类型获取对应的Java类型
     *
     * @param columnType 列类型
     * @return 对应的Java类型
     */
    public static String getJavaTypeByColumnType(String columnType) {
        JavaTypeEnum javaTypeEnum = typeMap.get(columnType);
        if (javaTypeEnum != null) {
            return javaTypeEnum.getJavaType();
        }
        return null;
    }

    /**
     * 根据Java类型获取对应的TypeScript类型
     *
     * @param javaType Java类型
     * @return 对应的TypeScript类型
     */
    public static String getTsTypeByJavaType(String javaType) {
        for (JavaTypeEnum javaTypeEnum : JavaTypeEnum.values()) {
            if (javaTypeEnum.getJavaType().equals(javaType)) {
                return javaTypeEnum.getTsType();
            }
        }
        return null;
    }
}
