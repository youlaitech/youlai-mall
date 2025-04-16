package com.youlai.mall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.converter.SpuImageConverter;
import com.youlai.mall.product.mapper.SpuImageMapper;
import com.youlai.mall.product.model.entity.SpuImageEntity;
import com.youlai.mall.product.service.SpuImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 商品图片服务实现类
 *
 * @author Ray.Hao
 * @since 2024/04/14
 */
@Service
@RequiredArgsConstructor
public class SpuImageServiceImpl extends ServiceImpl<SpuImageMapper, SpuImageEntity> implements SpuImageService {

    private final SpuImageConverter spuImageConverter;

    /**
     * 保存商品图册
     *
     * @param spuId   商品ID
     * @param imgUrls 商品图册
     */

    @Override
    @Transactional
    public void saveSpuImages(Long spuId, List<String> imgUrls) {
        // 删除旧的商品图册
        this.remove(new LambdaQueryWrapper<SpuImageEntity>()
                .eq(SpuImageEntity::getSpuId, spuId));

        // 2. 保存新图册
        if (CollectionUtil.isNotEmpty(imgUrls)) {
            List<SpuImageEntity> spuImageEntities = IntStream.range(0, imgUrls.size())
                    .mapToObj(i -> {
                        SpuImageEntity image = new SpuImageEntity();
                        image.setSpuId(spuId);
                        image.setImgUrl(imgUrls.get(i));
                        image.setSort(i + 1);  // 从1开始编号
                        return image;
                    })
                    .collect(Collectors.toList());

            this.saveBatch(spuImageEntities);
        }
    }
}
