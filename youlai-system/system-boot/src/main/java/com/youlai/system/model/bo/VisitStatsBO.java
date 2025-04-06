package com.youlai.system.model.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 访问量统计业务对象
 *
 * @author Ray.Hao
 * @since 2024/7/2
 */
@Getter
@Setter
public class VisitStatsBO {

    @Schema(description = "今日访问量 (PV)")
    private Integer todayCount;

    @Schema(description = "累计访问量 ")
    private Integer totalCount;

    @Schema(description = "页面访问量增长率")
    private BigDecimal growthRate;

}
