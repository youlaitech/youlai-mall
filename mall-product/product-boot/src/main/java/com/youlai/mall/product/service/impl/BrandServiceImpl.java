package com.youlai.mall.product.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.core.model.Option;
import com.youlai.mall.product.converter.BrandConverter;
import com.youlai.mall.product.mapper.BrandMapper;
import com.youlai.mall.product.model.entity.BrandEntity;
import com.youlai.mall.product.model.query.BrandPageQuery;
import com.youlai.mall.product.model.vo.BrandPageVO;
import com.youlai.mall.product.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 品牌接服务现类
 *
 * @author Ray.Hao
 * @since 2024/5/4
 */
@Service
@RequiredArgsConstructor
public class BrandServiceImpl extends ServiceImpl<BrandMapper, BrandEntity> implements BrandService {

    private final BrandConverter brandConverter;

    /**
     * 品牌分页列表
     *
     * @param queryParams 查询参数
     * @return 品牌分页列表
     */
    @Override
    public Page<BrandPageVO> getBrandPage(BrandPageQuery queryParams) {
        String keywords = queryParams.getKeywords();
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        Page<BrandEntity> page = this.page(new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<BrandEntity>().like(StrUtil.isNotBlank(keywords), BrandEntity::getName, keywords)
                        .orderByAsc(BrandEntity::getSort)
        );
        return brandConverter.toPageVo(page);
    }

    /**
     * 品牌下拉选项
     */
    @Override
    public List<Option<Long>> listBrandOptions(){
        List<BrandEntity> list = this.baseMapper.listBrandOptions();
        return brandConverter.toOption(list);
    }
}
