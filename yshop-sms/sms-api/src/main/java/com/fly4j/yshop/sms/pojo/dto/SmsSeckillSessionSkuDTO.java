package com.fly4j.yshop.sms.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fly4j.yshop.sms.pojo.entity.SmsSeckillSessionSku;
import lombok.Data;

import java.util.List;

/**
 * 秒杀商品保存传输层实体
 *
 * @author fly2021【xianrui0365@163.com】
 * @date 2020-03-31 10:32
 **/

@Data
public class SmsSeckillSessionSkuDTO {

    /**
     * 秒杀活动ID
     */
    private Long promotion_id;

    /**
     * 秒杀时间段ID
     */
    private Long promotion_session_id;

    /**
     * 秒杀商品集合
     */
    private List<SmsSeckillSessionSku> sku_list;

}
