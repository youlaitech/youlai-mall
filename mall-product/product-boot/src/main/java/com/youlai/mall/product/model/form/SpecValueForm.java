package com.youlai.mall.product.model.form;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 表单对象
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
public class SpecValueForm implements Serializable {

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
