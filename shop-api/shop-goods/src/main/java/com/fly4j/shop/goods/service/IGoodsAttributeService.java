package com.fly4j.shop.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.shop.goods.pojo.entity.GoodsAttribute;
import com.fly4j.shop.goods.pojo.vo.CascaderVO;

import java.util.List;

public interface IGoodsAttributeService extends IService<GoodsAttribute> {

     List<CascaderVO> cascader();

    Page<GoodsAttribute> selectPage(Page<GoodsAttribute> page, GoodsAttribute goodsAttribute);
}
