package com.youlai.mall.product.service.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.constant.GlobalConstants;
import com.youlai.mall.product.converter.CategoryConverter;
import com.youlai.mall.product.mapper.CategoryMapper;
import com.youlai.mall.product.model.entity.CategoryEntity;
import com.youlai.mall.product.model.vo.client.ClientCategoryVO;
import com.youlai.mall.product.service.client.ClientCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientCategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements ClientCategoryService {

    private final CategoryConverter categoryConverter;

    /**
     * 客户端分类列表
     *
     * @return APP端分类列表
     */
    @Override
    public List<ClientCategoryVO> listCategories() {
        // 1. 查询可见分类并按sort升序排列
        List<CategoryEntity> categories = this.list(new LambdaQueryWrapper<CategoryEntity>()
                .eq(CategoryEntity::getIsVisible, GlobalConstants.STATUS_YES)
                .orderByAsc(CategoryEntity::getSort)
        );

        // 2. 构建ID到VO的映射关系（避免递归查询数据库）
        Map<Long, ClientCategoryVO> voMap = new HashMap<>(categories.size());

        // 3. 先转换所有Entity为VO对象
        categories.forEach(entity -> {
            ClientCategoryVO vo = categoryConverter.toClientCategoryVO(entity);
            vo.setChildren(new ArrayList<>()); // 初始化空列表避免NPE
            voMap.put(entity.getId(), vo);
        });

        // 4. 构建树形结构（时间复杂度O(n)）
        List<ClientCategoryVO> result = new ArrayList<>();
        categories.forEach(entity -> {
            ClientCategoryVO current = voMap.get(entity.getId());
            Long parentId = entity.getParentId();

            if (parentId.equals(0L)) {
                // 5. 添加顶级分类到结果集
                result.add(current);
            } else {
                // 6. 将当前分类添加到父分类的子节点列表
                ClientCategoryVO parent = voMap.get(parentId);
                if (parent != null) {
                    parent.getChildren().add(current);
                } else {
                    // 7. 异常数据处理：记录日志或抛出业务异常
                    log.warn("发现孤立分类: ID={}, parentId={}", entity.getId(), parentId);
                }
            }
        });

        return result;
    }

}
