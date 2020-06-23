package com.fly4j.yshop.common.result;

import java.io.Serializable;

/**
 * @author haoxr
 * @date 2020-06-23
 **/
public class Result<T> implements Serializable {

    private String code;

    private T data;

    private String msg;


    public static <T> Result<T> success(){
        return success(null);
    }




}
