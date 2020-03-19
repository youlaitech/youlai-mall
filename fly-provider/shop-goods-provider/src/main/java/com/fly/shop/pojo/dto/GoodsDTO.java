package com.fly.shop.pojo.dto;

import com.fly.shop.pojo.entity.Goods;
import com.fly.shop.pojo.vo.GoodsAttributeValueVO;
import com.fly.shop.pojo.vo.GoodsFullReductionVO;
import com.fly.shop.pojo.vo.GoodsLadderVO;
import com.fly.shop.pojo.vo.GoodsSkuStockVO;
import lombok.Data;

import java.util.List;

/**
 * @description: 商品添加的参数
 * @author: Mr.
 * @create: 2020-03-14 14:13
 **/
@Data
public class GoodsDTO extends Goods {
    /**
     * 商品阶梯价格设置
     */
    private List<GoodsLadderVO> goodsLadderList;
    /**
     * 商品满减价格设置
     */
    private List<GoodsFullReductionVO> goodsFullReductionList;
    /**
     * 商品的sku库存信息
     */
    private List<GoodsSkuStockVO> skuStockList;
    /**
     * 商品参数及自定义规格属性
     */
    private List<GoodsAttributeValueVO> goodsAttributeValueList;


}
