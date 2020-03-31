package com.fly4j.shop.marketing.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

/**
 * 秒杀商品保存传输层实体
 *
 * @author fly2021【xianrui0365@163.com】
 * @date 2020-03-31 10:32
 **/

@Data
public class SpikePeriodGoodsListDTO {

    /**
     * 秒杀活动ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long spikeId;

    /**
     * 秒杀时间段ID
     */
    private Long spikePeriodId;

    /**
     * 秒杀商品集合
     */
    private List<SpikePeriodGoodsDTO> spikePeriodGoodsList;

}
