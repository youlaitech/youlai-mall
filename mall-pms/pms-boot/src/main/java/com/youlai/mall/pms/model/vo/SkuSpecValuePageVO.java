package com.youlai.mall.pms.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * SKU规格值 分页VO
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
public class SkuSpecValuePageVO implements Serializable {

    private static final long serialVersionUID = 1L;

        /**
         * SKU ID
         */

    private Long skuId;

        /**
         * 规格值 ID
         */

    private Long specValueId;
}
