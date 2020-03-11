package com.fly.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fly.shop.pojo.entity.GoodsCategory;
import com.fly.shop.pojo.entity.GoodsCategoryAttributeRelation;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsCategoryAttributeRelationMapper extends BaseMapper<GoodsCategoryAttributeRelation> {

}
