package com.youlai.codegen.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * 数据库枚举
 *
 * @author Ray
 * @since 2.10.0
 */
@Getter
@RequiredArgsConstructor
public enum DatabaseEnum {

    /**
     * 输入框
     */
    SYSTEM("youlai_system", "system"),

    /**
     * 会员数据库
     */
    MEMBER("youlai_mall_ums", "member"),

    /**
     * 商品数据库
     */
    PRODUCT("youlai_mall_pms", "product"),

    /**
     * 订单数据库
     */
    ORDER("youlai_mall_oms", "order"),

    /**
     * 营销数据库
     */
    SALE("youlai_mall_sms", "sale");


    /**
     * 数据库名称
     */
    private final String database;

    /**
     * 模块名称
     */
    private final String modelName;


    /**
     * 根据数据库名称获取模块名称
     *
     * @param database 数据库名称
     * @return 模块名称
     */
    public static String getModelNameByDatabase(String database) {
        return Arrays.stream(DatabaseEnum.values())
                .filter(enumValue -> enumValue.database.equals(database))
                .map(DatabaseEnum::getModelName)
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取所有数据库名称的集合
     *
     * @return 数据库名称集合
     */
    public static List<String> getDatabases() {
        return Arrays.stream(DatabaseEnum.values())
                .map(DatabaseEnum::getDatabase)
                .toList();
    }
}
