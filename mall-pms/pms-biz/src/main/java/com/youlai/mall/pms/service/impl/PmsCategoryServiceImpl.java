package com.youlai.mall.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.entity.PmsCategory;
import com.youlai.mall.pms.mapper.PmsCategoryMapper;
import com.youlai.mall.pms.service.IPmsCategoryService;
import com.youlai.mall.pms.vo.PmsCategoryVO;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private List<PmsCategoryVO> recursionForList(long l, List<PmsCategory> categoryList) {
        return null;
    }
}
