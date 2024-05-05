package com.youlai.mall.product.model.form;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 商品图片 表单对象
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
public class SpuImageForm implements Serializable {

    /**
     * 序列化版本号，版本号不一致会导致反序列化失败
     * 如果对象结构发生变化，建议修改此版本号
     */
    private static final long serialVersionUID = 1L;

    @Schema(description = "商品ID")
    private Long spuId;

    @Schema(description = "图片地址")
    private String imgUrl;

    @Schema(description = "排序")
    private Integer sort;
}
