package com.youlai.mall.pms.pojo.vo;

import com.youlai.common.core.base.BaseVO;
import lombok.Data;

/**
 * @author huawei
 * @desc 商品库存信息
 * @email huawei_code@163.com
 * @date 2021/1/21
 */
@Data
public class SkuStockVO extends BaseVO {

    private Long skuId;

    private Integer number;
}
