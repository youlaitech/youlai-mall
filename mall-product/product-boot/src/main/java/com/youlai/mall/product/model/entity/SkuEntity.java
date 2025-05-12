package com.youlai.mall.product.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.common.base.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * Sku 实体类，代表商品的一个具体的库存单元。
 * Sku 通常包含特定的属性组合，如颜色、尺寸等，与Spu相对，
 * Spu是指商品的标准化信息，而Sku是在此基础上的特定属性的实体。
 *
 * @author Ray.Hao
 * @since 2024/4/6
 */
@TableName("pms_sku")
@Getter
@Setter
public class SkuEntity extends BaseEntity {

  /**
   * SKU编号
   */
  private String skuCode;

  /**
   * 与该SKU相关联的SPU的ID。
   */
  private Long spuId;

  /**
   * 规格组合的MD5哈希值
   */
  private String specHash;

  /**
   * 商品SKU的价格单位（分）
   */
  private Long price;

  /**
   * 商品SKU的库存数量
   */
  private Integer stock;

  /**
   * 商品SKU的库存锁定数量
   */
  private Integer stockLocked;

  /**
   * 商品SKU的销量
   */
  private Integer stockSold;

  /**
   * 商品SKU的图片URL地址。
   */
  private String imgUrl;

  /**
   * 逻辑删除标识(0:未删除 1:已删除)
   */
  private Integer isDeleted;
}
