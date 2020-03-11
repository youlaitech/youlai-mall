package com.fly.shop.services;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fly.shop.pojo.entity.GoodsAttribute;
import com.fly.shop.pojo.entity.GoodsCategory;
import com.fly.shop.pojo.vo.CascaderVO;

import java.util.List;

public interface IGoodsAttributeService extends IService<GoodsAttribute> {

     List<CascaderVO> cascader();

    Page<GoodsAttribute> selectPage(Page<GoodsAttribute> page, GoodsAttribute goodsAttribute);
}
