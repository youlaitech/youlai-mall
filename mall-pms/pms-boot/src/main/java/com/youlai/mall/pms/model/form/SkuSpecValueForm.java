package com.youlai.mall.pms.model.form;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * SKU规格值 表单对象
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Getter
@Setter
public class SkuSpecValueForm implements Serializable {

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
