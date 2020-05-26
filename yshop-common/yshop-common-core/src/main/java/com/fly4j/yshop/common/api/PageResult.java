package com.fly4j.yshop.common.api;


import lombok.Data;

@Data
public class PageResult<T> extends Result {

    private Long total;

    public static <T> PageResult<T> ok(T data, Long total) {
        PageResult<T> pageResult = new PageResult();
        pageResult.setCode(ApiErrorCode.SUCCESS.getCode());
        pageResult.setData(data);
        pageResult.setMsg(ApiErrorCode.SUCCESS.getMsg());
        pageResult.setTotal(total);
        return pageResult;
    }
}