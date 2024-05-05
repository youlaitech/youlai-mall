package com.youlai.mall.product.model.dto;


import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

/**
 *  DTO
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
public class SpuSpecValueDTO implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
         * 规格值ID
         */

private Long id;

        /**
         * 规格ID
         */

private Long specId;

        /**
         * 规格值
         */

private String value;
}
