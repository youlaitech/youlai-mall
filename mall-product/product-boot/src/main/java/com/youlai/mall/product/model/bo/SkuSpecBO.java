package com.youlai.mall.product.model.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * SKU 规格值实体类
 *
 * @author Ray.Hao
 * @since 2024/4/14
 */
@TableName("pms_sku_spec")
@Data
public class SkuSpecBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 规格ID
     */
    private Long specId;

    /**
     * 规格名称 如：颜色
     */
    private String specName;

    /**
     * 规格值 如：红色
     */
    private String specValue;

    /**
     * 是否主规格(0-否 1-是)
     */
    private Integer isMain;

    /**
     * 规格图片，只有主规格才有，如：红色为红色图片
     */
    private String imgUrl;
}
