package com.youlai.mall.pms.pojo.dto;

import lombok.Data;

/**
 * @author huawei
 * @desc
 * @email huawei_code@163.com
 * @date 2021/1/13
 */
@Data
public class SkuDTO {

    private Long id;

    private String code;

    private String title;

    private String pic;

    private Long price;

    private Long spuId;



}
