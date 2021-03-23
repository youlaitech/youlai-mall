package com.youlai.mall.oms.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 订单商品信息表
 *
 * @author huawei
 * @email huawei_code@163.com
 * @date 2020-12-30 22:31:10
 */
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OmsOrderItem extends BaseEntity {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * order_id
     */
    private Long orderId;
    /**
     * 商品sku id
     */
    private Long skuId;
    /**
     * 商品sku编号
     */
    private String skuCode;


    private String skuName;

    /**
     * 商品sku图片
     */
    private String skuPic;
    /**
     * 商品sku价格(分)
     */
    private Long skuPrice;
    /**
     * 商品购买的数量
     */
    private Integer skuQuantity;
    /**
     * 商品sku总价格(分)
     */
    private Long skuTotalPrice;
    /**
     * spu_id
     */
    private Long spuId;
    /**
     * spu_name
     */
    private String spuName;
    /**
     * spu_pic
     */
    private String spuPic;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 商品分类id
     */
    private Long categoryId;
    /**
     * 商品分类名称
     */
    private String categoryName;
    /**
     * 逻辑删除【0->正常；1->已删除】
     */
    private Integer deleted;

}
