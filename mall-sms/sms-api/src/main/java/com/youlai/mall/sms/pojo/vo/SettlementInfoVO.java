package com.youlai.mall.sms.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xinyi
 * @desc: 优惠券结算信息模型
 * @date 2021/7/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettlementInfoVO {

    private Long userId;

    private List<GoodsInfo> goodsInfos;


    private List<CouponAndTemplateInfo> couponAndTemplateInfos;

    private Boolean employ;
    private Long pay;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CouponAndTemplateInfo {
        private Long id;

        private CouponTemplateVO template;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GoodsInfo {
        private Long id;

        private Long price;

        private Integer count;

        private String type;
    }
}
