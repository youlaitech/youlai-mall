package com.fly.shop.services;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fly.shop.pojo.dto.GoodsCategoryDTO;
import com.fly.shop.pojo.entity.GoodsCategory;
import com.fly.shop.pojo.vo.TreeSelectVO;

import java.util.List;

public interface IGoodsCategoryService extends IService<GoodsCategory> {

    Page<GoodsCategory> selectPage(Page<GoodsCategory> page, GoodsCategory goodsCategory);

    List<GoodsCategoryDTO> selectList(GoodsCategory goodsCategory);

    List<TreeSelectVO> treeSelect(GoodsCategory goodsCategory);

}
