package com.youlai.mall.pms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.converter.SpuImageConverter;
import com.youlai.mall.pms.mapper.SpuImageMapper;
import com.youlai.mall.pms.model.entity.SpuImage;
import com.youlai.mall.pms.model.form.SpuForm;
import com.youlai.mall.pms.service.SpuImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 商品图片服务实现类
 *
 * @author Ray Hao
 * @since 2024/04/14
 */
@Service
@RequiredArgsConstructor
public class SpuImageServiceImpl extends ServiceImpl<SpuImageMapper, SpuImage> implements SpuImageService {

    private final SpuImageConverter spuImageConverter;


    /**
     * 保存商品图册
     *
     * @param spuId 商品ID
     * @param formImages 商品图册
     */

    @Override
    public void saveSpuImages(Long spuId, List<SpuForm.Image> formImages) {
        // 根据 SPU ID 获取商品图册旧图片
        List<SpuImage> oldImages = this.list(new LambdaQueryWrapper<SpuImage>()
                .eq(SpuImage::getSpuId, spuId));

        // 转换为实体
        List<SpuImage> newImages = spuImageConverter.formImage2Entity(formImages);

        if (CollectionUtil.isNotEmpty(newImages)) {

            // 新增图片处理
            List<SpuImage> addImages = newImages.stream()
                    .filter(item -> item.getId() == null)
                    .peek(item -> item.setSpuId(spuId))
                    .toList();

            if(CollectionUtil.isNotEmpty(addImages)){
                this.saveBatch(addImages);
            }

            // 构建新图片ID集合，以便快速检查
            Set<Long> newImageIds = newImages.stream()
                    .map(SpuImage::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            // 确定需要删除的图片ID集合：旧图片ID集合中存在，新图片ID集合中不存在
            List<Long> removeImageIds = oldImages.stream()
                    .map(SpuImage::getId)
                    .filter(id -> !newImageIds.contains(id))
                    .collect(Collectors.toList());

            if (CollectionUtil.isNotEmpty(removeImageIds)) {
                this.remove(new LambdaQueryWrapper<SpuImage>()
                        .eq(SpuImage::getSpuId, spuId)
                        .in(SpuImage::getId, removeImageIds));
            }
        }
    }
}
