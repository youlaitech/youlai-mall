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
public class SpuSpecBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 规格主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * SPU ID
     */
    private Long spuId;

    /**
     * 规格名称
     */
    private String name;
}
