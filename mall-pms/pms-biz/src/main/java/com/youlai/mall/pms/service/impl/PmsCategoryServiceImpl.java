package com.youlai.mall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.entity.PmsCategory;
import com.youlai.mall.pms.mapper.PmsCategoryMapper;
import com.youlai.mall.pms.service.IPmsCategoryService;
import com.youlai.mall.pms.vo.PmsCategoryVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PmsCategoryServiceImpl extends ServiceImpl<PmsCategoryMapper, PmsCategory> implements IPmsCategoryService {

    @Override
    public List<PmsCategoryVO> list(PmsCategory category) {
        List<PmsCategory> categoryList = this.list(
                new LambdaQueryWrapper<PmsCategory>()
                        .eq(category.getStatus() != null, PmsCategory::getStatus, category.getStatus())
        );
        List<PmsCategoryVO> list = recursionForList(0l, categoryList);
        return list;
    }

    private static List<PmsCategoryVO> recursionForList(Long parentId, List<PmsCategory> categoryList) {
        List<PmsCategoryVO> list = new ArrayList<>();
        Optional.ofNullable(categoryList)
                .ifPresent(categories ->
                        categories.stream().filter(category ->
                                category.getParentId().equals(parentId))
                                .forEach(category -> {
                                    PmsCategoryVO categoryVO = new PmsCategoryVO();
                                    BeanUtil.copyProperties(category, categoryVO);
                                    List<PmsCategoryVO> children = recursionForList(category.getId(), categoryList);
                                    categoryVO.setChildren(children);
                                    list.add(categoryVO);
                                }));
        return list;
    }
}
