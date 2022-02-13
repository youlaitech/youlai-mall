package com.youlai.mall.pms.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.common.base.BaseEntity;
import lombok.Data;

/**
 * 商品库存单元实体
 *
 * @author haoxr
 * @date 2022/2/6
 */
@Data
public class PmsSku extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 库存单元编号
     */
    private String skuSn;

    /**
     * SKU 名称
     */
    private String name;

    /**
     * SPU ID
     */
    private Long spuId;

    /**
     * 规格ID，多个使用英文逗号(,)分割
     */
    private String specIds;

    /**
     * 商品价格(单位：分)
     */
    private Long price;

    /**
     * 库存数量
     */
    private Integer stockNum;

    /**
     * 锁定库存数量
     */
    private Integer lockedStockNum;

    /**
     * 商品图片地址
     */
    private String picUrl;
}
