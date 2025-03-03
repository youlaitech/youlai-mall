package com.youlai.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 商品属性
 * <p>
 * <strong>更新记录：</strong>
 * <ul>
 *     <li>2024/4/24 - Ray Hao 修改了商品属性表结构，将“规格”从属性表拆分出来。</li>
 * </ul>
 * </p>
 *
 * @author Ray.Hao
 * @since 2020/11/06
 */
@TableName("pms_spu_attr")
@Getter
@Setter
public class SpuAttr implements Serializable {

    /**
     * 销售属性 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * SPU ID
     */
    private Long spuId;

    /**
     * 属性ID
     */
    private Long attrId;

    /**
     * 属性值
     */
    private String attrValue;

}
