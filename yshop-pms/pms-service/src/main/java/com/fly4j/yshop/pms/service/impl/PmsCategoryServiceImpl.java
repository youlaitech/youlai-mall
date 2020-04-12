package com.fly4j.yshop.pms.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.common.constant.Constants;
import com.fly4j.yshop.pms.mapper.PmsCategoryMapper;
import com.fly4j.yshop.pms.pojo.entity.PmsCategory;
import com.fly4j.yshop.pms.pojo.vo.CascaderVO;
import com.fly4j.yshop.pms.service.IPmsCategoryService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PmsCategoryServiceImpl extends ServiceImpl<PmsCategoryMapper, PmsCategory> implements IPmsCategoryService {


    /**
     * 类型级联列表
     */
    @Override
    public List<CascaderVO> cascadeList() {
        List<CascaderVO> cascadeList = new LinkedList<>();
        List<PmsCategory> categoryList = this.list();
        if (categoryList != null && categoryList.size() > 0) {
            List<PmsCategory> categoryListL1 = categoryList.stream()
                    .filter(category -> Constants.CATEGORY_LEVEL1.equals(category.getLevel())).collect(Collectors.toList());

            List<PmsCategory> categoryListL2 = categoryList.stream()
                    .filter(category -> Constants.CATEGORY_LEVEL2.equals(category.getLevel())).collect(Collectors.toList());
            categoryListL1.forEach(l1 -> {
                CascaderVO cascaderVO = new CascaderVO().setValue(null).setLabel(l1.getName());
                List<CascaderVO> children = new LinkedList<>();
                List<PmsCategory> listL2 = categoryListL2.stream().filter(l2 -> l2.getParent_id().equals(l1.getId())).collect(Collectors.toList());
                if (listL2 != null && listL2.size() > 0) {
                    listL2.forEach(l2 -> children.add(
                            new CascaderVO()
                                    .setValue(l2.getId().toString())
                                    .setLabel(l1.getName()
                                    )));
                }
                cascaderVO.setChildren(children);
                cascadeList.add(cascaderVO);
            });
        }
        return cascadeList;
    }
}
