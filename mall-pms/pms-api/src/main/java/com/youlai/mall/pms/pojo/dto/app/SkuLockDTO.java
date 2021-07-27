package com.youlai.mall.pms.pojo.dto.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 锁定库存
 * @author haoxr
 * @createTime 2021-03-07 15:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuLockDTO {

    private Long skuId;

    private Integer count;

    private String orderToken;

    private Boolean locked;

}
