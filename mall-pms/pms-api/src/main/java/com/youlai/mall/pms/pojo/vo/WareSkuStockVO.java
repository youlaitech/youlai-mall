package com.youlai.mall.pms.pojo.vo;

import com.youlai.common.core.base.BaseVO;
import lombok.Data;

import java.util.List;

/**
 * @author huawei
 * @desc 锁定库存VO
 * @email huawei_code@163.com
 * @date 2021/1/16
 */
@Data
public class WareSkuStockVO extends BaseVO {

    private List<SkuStockVO> items;
}
