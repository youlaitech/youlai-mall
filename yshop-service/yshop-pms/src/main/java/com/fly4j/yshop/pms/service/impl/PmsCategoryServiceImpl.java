package com.fly4j.yshop.pms.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly4j.yshop.common.constant.Constants;
import com.fly4j.yshop.pms.mapper.PmsCategoryMapper;
import com.fly4j.yshop.pms.pojo.dto.admin.PmsCategoryDTO;
import com.fly4j.yshop.pms.pojo.entity.PmsCategory;
import com.fly4j.yshop.pms.pojo.vo.CascaderVO;
import com.fly4j.yshop.pms.service.IPmsCategoryService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class PmsCategoryServiceImpl extends ServiceImpl<PmsCategoryMapper, PmsCategory> implements IPmsCategoryService {


    @Override
    public List<PmsCategoryDTO> treeList(Map<String, Object> paramMap) {
        List<PmsCategoryDTO> list = new LinkedList<>();

        List<PmsCategory> categoryList = this.list(new LambdaQueryWrapper<PmsCategory>()
                .like(paramMap.get("name") != null, PmsCategory::getName, paramMap.get("name").toString())
                .orderByAsc(PmsCategory::getSort)
        );
        if (categoryList != null && categoryList.size() > 0) {
            List<PmsCategory> categoryListL1 = categoryList.stream()
                    .filter(category -> Constants.CATEGORY_LEVEL1.equals(category.getLevel())).collect(Collectors.toList());

            List<PmsCategory> categoryListL2 = categoryList.stream()
                    .filter(category -> Constants.CATEGORY_LEVEL2.equals(category.getLevel())).collect(Collectors.toList());
            categoryListL1.forEach(l1 -> {
                PmsCategoryDTO categoryDTO = new PmsCategoryDTO();
                BeanUtil.copyProperties(l1, categoryDTO);
                List<PmsCategoryDTO> children = new LinkedList<>();
                categoryListL2.forEach(l2 -> {
                    if (l2.getParent_id().equals(l1.getId())) {
                        PmsCategoryDTO child = new PmsCategoryDTO();
                        BeanUtil.copyProperties(l2, child);
                        children.add(child);
                    }
                });
                categoryDTO.setChildren(children);
                list.add(categoryDTO);
            });
        }
        return list;
    }

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
                                    .setLabel(l2.getName()
                                    )));
                }
                if (children != null) {
                    cascaderVO.setChildren(children);
                }
                cascadeList.add(cascaderVO);
            });
        }
        return cascadeList;
    }
}
