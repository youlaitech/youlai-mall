package com.youlai.mall.pms.model.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * SKU规格值
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
public class SkuSpecValueBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * SKU ID
     */
    @TableId("sku_id")
    private Long skuId;

    /**
     * 规格值 ID
     */
    @TableId("spec_value_id")
    private Long specValueId;
}
