package com.youlai.mall.pms.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Gadfly
 * @since 2021-08-10 15:44
 */
@Data
public class ProductHistoryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String picUrl;
}
