package com.fly.common.enums;

/**
 * 数据源枚举
 */
public enum DataSourceType {

    mysql("mysql"),oracle("oracle");

    private String value;

    DataSourceType(String value){
        this.value=value;
    }

    public String getValue(){
        return value;
    }
}
