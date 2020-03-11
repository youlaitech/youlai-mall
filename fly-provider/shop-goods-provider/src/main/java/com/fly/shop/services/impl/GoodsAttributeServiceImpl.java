package com.fly.shop.services.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.shop.dao.GoodsAttributeMapper;
import com.fly.shop.pojo.entity.GoodsAttribute;
import com.fly.shop.pojo.entity.GoodsAttributeType;
import com.fly.shop.pojo.vo.CascaderVO;
import com.fly.shop.services.IGoodsAttributeService;
import com.fly.shop.services.IGoodsAttributeTypeService;
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
