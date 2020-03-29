package com.fly4j.shop.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fly4j.shop.goods.pojo.dto.GoodsCategoryDTO;
import com.fly4j.shop.goods.pojo.entity.GoodsCategory;
import com.fly4j.shop.goods.pojo.vo.TreeSelectVO;

import java.util.List;

public interface IGoodsCategoryService extends IService<GoodsCategory> {

    Page<GoodsCategory> selectPage(Page<GoodsCategory> page, GoodsCategory goodsCategory);

    List<GoodsCategoryDTO> selectList(GoodsCategory goodsCategory);

    List<TreeSelectVO> treeSelect(GoodsCategory goodsCategory);

    boolean update(GoodsCategoryDTO goodsCategoryDTO);

    boolean add(GoodsCategoryDTO goodsCategoryDTO);
}
