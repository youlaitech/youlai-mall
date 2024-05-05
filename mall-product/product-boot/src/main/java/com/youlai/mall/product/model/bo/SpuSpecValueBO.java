package com.youlai.mall.product.model.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
public class SpuSpecValueBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 规格值ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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
