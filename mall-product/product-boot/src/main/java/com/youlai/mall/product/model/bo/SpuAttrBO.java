package com.youlai.mall.product.model.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * SKU
 *
 * @author Ray.Hao
 * @since 2024/4/14
 */
@Data
public class SpuAttrBO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * SKU ID
     */
    private Long spuId;

    /**
     * 属性名称 如：屏幕尺寸
     */
    private String attrName;

    /**
     * 属性值 如：6.5英寸
     */
    private String attrValue;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
