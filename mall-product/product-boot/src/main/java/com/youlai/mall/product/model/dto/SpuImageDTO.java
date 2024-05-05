package com.youlai.mall.product.model.dto;


import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

/**
 * 商品图片 DTO
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
public class SpuImageDTO implements Serializable {

    private static final long serialVersionUID = 1L;


private Long spuId;


private String imgUrl;
}
