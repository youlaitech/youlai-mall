package com.youlai.mall.sms.pojo.vo;

import com.youlai.mall.pms.pojo.dto.SkuInfoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huawei
 * @desc 秒杀商品模型
 * @email huawei_code@163.com
 * @date 2021/3/7
 */
@ApiModel(value = "秒杀商品模型",description = "秒杀商品模型")
@Data
public class SmsSeckillSkuVO {

    /**
     * id
     */
    @ApiModelProperty
    private Long id;

    /**
     * 活动场次id
     */
    private Long sessionId;

    /**
     * 商品id
     */
    private Long skuId;

    /**
     * 秒杀随机码
     */
    private String randomCode;

    /**
     * 秒杀价格
     */
    private Long seckillPrice;

    /**
     * 秒杀总量
     */
    private Integer seckillCount;

    /**
     * 每人限购数量
     */
    private Integer seckillLimit;

    /**
     * 排序
     */
    private Integer seckillSort;

    /**
     * 秒杀开始时间
     */
    private Long startTime;

    /**
     * 秒杀结束时间
     */
    private Long endTime;

    /**
     * 秒杀商品详情
     */
    private SkuInfoDTO skuInfo;
}
