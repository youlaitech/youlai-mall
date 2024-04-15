package com.youlai.mall.pms.model.dto;


import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * SKU规格值 DTO
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
public class SkuSpecValueDTO implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
         * SKU ID
         */

private Long skuId;

        /**
         * 规格值 ID
         */

private Long specValueId;
}
