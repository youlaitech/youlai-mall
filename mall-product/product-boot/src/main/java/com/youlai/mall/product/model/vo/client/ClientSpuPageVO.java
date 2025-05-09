package com.youlai.mall.product.model.vo.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 【应用端】商品分页视图对象
 *
 * @author <a href="mailto:xianrui0365@163.com">Ray</a>
 * @since 2021/8/8
 */
@Schema(description = "商品分页对象")
@Data
public class ClientSpuPageVO {

    @Schema(description="商品ID")
    private Long id;

    @Schema(description="商品名称")
    private String name;

    @Schema(description="商品价格(单位：分)")
    private Long price;

    @Schema(description="销量")
    private Integer sales;

    @Schema(description="图片地址")
    private String imgUrl;

    @Schema(description="商品描述")
    private String description;

    @Schema(description="单位")
    private String unit;

}
