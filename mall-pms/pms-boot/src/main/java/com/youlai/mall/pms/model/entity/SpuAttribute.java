package com.youlai.mall.pms.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.base.BaseEntity;
import lombok.Data;

/**
 * 商品属性
 *
 * @author haoxr
 * @since 2020/11/06
 */
@TableName("pms_spu_attribute")
@Data
public class SpuAttribute extends BaseEntity {

    /**
     * 商品ID
     */
    private Long spuId;
    /**
     * 属性ID
     */
    private Long attributeId;
    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性值
     */
    private String value;
    /**
     * 属性类型(1:规格;2:属性;)
     */
    private Integer type;

    /**
     * 规格图片地址
     */
    private String imgUrl;

}
