package com.youlai.mall.product.model.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 *  VO
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
public class SpecValueVO implements Serializable {

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
