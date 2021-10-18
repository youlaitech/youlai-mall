package com.youlai.mall.pms.pojo.vo.app;

import lombok.Data;

/**
 * 商品列表页-商品基础信息
 *
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 * @date 2021/8/8
 */
@Data
public class GoodsVO {
    private Long id;
    private String name;
    private Long price;
    private Integer sales;
    private String picUrl;
}
