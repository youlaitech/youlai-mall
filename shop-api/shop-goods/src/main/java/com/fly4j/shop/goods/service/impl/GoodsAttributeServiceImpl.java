package com.fly4j.shop.goods.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.shop.goods.mapper.GoodsAttributeMapper;
import com.fly4j.shop.goods.pojo.entity.GoodsAttribute;
import com.fly4j.shop.goods.pojo.entity.GoodsAttributeType;
import com.fly4j.shop.goods.pojo.vo.CascaderVO;
import com.fly4j.shop.goods.service.IGoodsAttributeService;
import com.fly4j.shop.goods.service.IGoodsAttributeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class GoodsAttributeServiceImpl extends ServiceImpl<GoodsAttributeMapper, GoodsAttribute> implements IGoodsAttributeService {

    @Autowired
    private IGoodsAttributeTypeService iGoodsAttributeTypeService;

    @Override
    public Page<GoodsAttribute> selectPage(Page<GoodsAttribute> page, GoodsAttribute goodsAttribute) {
        List<GoodsAttribute> list = this.baseMapper.page(page, goodsAttribute);
        page.setRecords(list);
        return page;
    }

    /**
     * 商品类型和属性级联(总二级)
     */
    public List<CascaderVO> cascader() {
        List<CascaderVO> resultList = new LinkedList<>();
        List<GoodsAttributeType> typeList = iGoodsAttributeTypeService.list();
        List<GoodsAttribute> attributeList = this.list();
        if (typeList != null && typeList.size() > 0) {
            typeList.forEach(type -> {
                CascaderVO cascaderVO = new CascaderVO().setValue(null).setLabel(type.getAttributeTypeName());
                List<CascaderVO> children = new LinkedList<>();
                attributeList.forEach(attr -> {
                    if (attr.getAttributeTypeId().equals(type.getAttributeTypeId())) {
                        CascaderVO child = new CascaderVO()
                                .setValue(String.valueOf(attr.getAttributeId()))
                                .setLabel(attr.getAttributeName());
                        children.add(child);
                    }
                });
                cascaderVO.setChildren(children);
                resultList.add(cascaderVO);
            });
        }
        return resultList;
    }


}
