package com.youlai.lab.seata.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Seata模拟数据视图对象
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/4/17 16:51
 */
@ApiModel("Seata模拟数据视图对象")
@Data
public class SeataDataVO {

    @ApiModelProperty("商品库存信息")
    private StockInfo stockInfo;

    @ApiModelProperty("会员信息")
    private AccountInfo accountInfo;

    @ApiModelProperty("订单信息")
    private OrderInfo orderInfo;

    @ApiModel("商品库存信息")
    @Data
    public static class StockInfo{

        @ApiModelProperty("商品名称")
        private String name;
        @ApiModelProperty("商品图片")
        private String picUrl;
        @ApiModelProperty("库存数量")
        private Integer stockNum;

    }

    @ApiModel("订单信息")
    @Data
    public static class OrderInfo{
        @ApiModelProperty("订单编号")
        private String orderSn;
        @ApiModelProperty("订单状态")
        private Integer status;

    }

    @ApiModel("会员信息")
    @Data
    public static class AccountInfo{
        @ApiModelProperty("会员")
        private String nickName;
        @ApiModelProperty("订单状态")
        private String avatarUrl;
        @ApiModelProperty("订单状态")
        private Long balance;
    }

}
