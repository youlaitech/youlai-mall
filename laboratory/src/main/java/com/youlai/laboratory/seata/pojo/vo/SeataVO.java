package com.youlai.laboratory.seata.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Seata模拟数据视图对象
 *
 * @author haoxr
 * @since 2022/4/17 16:51
 */
@Schema(description = "Seata模拟数据视图对象")
@Data
public class SeataVO {

    @Schema(description="商品库存信息")
    private StockInfo stockInfo;

    @Schema(description="会员信息")
    private AccountInfo accountInfo;

    @Schema(description="订单信息")
    private OrderInfo orderInfo;

    @Schema(description = "商品库存信息")
    @Data
    public static class StockInfo{

        @Schema(description="商品名称")
        private String name;
        @Schema(description="商品图片")
        private String picUrl;
        @Schema(description="库存数量")
        private Integer stockNum;

    }

    @Schema(description = "订单信息")
    @Data
    public static class OrderInfo{
        @Schema(description="订单编号")
        private String orderSn;
        @Schema(description="订单状态")
        private Integer status;

    }

    @Schema(description = "会员信息")
    @Data
    public static class AccountInfo{
        @Schema(description="会员")
        private String nickName;
        @Schema(description="订单状态")
        private String avatarUrl;
        @Schema(description="订单状态")
        private Long balance;
    }

}
