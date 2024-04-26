package com.youlai.mall.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youlai.mall.pms.model.entity.Spec;
import com.youlai.mall.pms.mapper.SpuSpecMapper;
import com.youlai.mall.pms.model.form.SpuForm;
import com.youlai.mall.pms.service.SpuSpecService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.youlai.common.util.DateUtils;
import com.youlai.mall.pms.model.form.SpecForm;
import com.youlai.mall.pms.model.query.SpecPageQuery;
import com.youlai.mall.pms.model.bo.SpuSpecBO;
import com.youlai.mall.pms.model.vo.SpecPageVO;
import com.youlai.mall.pms.converter.SpecConverter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 服务实现类
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
@Service
@RequiredArgsConstructor
public class SpuSpecServiceImpl extends ServiceImpl<SpuSpecMapper, Spec> implements SpuSpecService {

    private final SpecConverter specConverter;

    /**
    * 获取分页列表
    *
    * @param queryParams 查询参数
    * @return {@link IPage<SpecPageVO>} 分页列表
    */
    @Override
    public IPage<SpecPageVO> listPagedSpecs(SpecPageQuery queryParams) {
    
        // 参数构建
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        Page<SpuSpecBO> page = new Page<>(pageNum, pageSize);

        // 格式化为数据库日期格式，避免日期比较使用格式化函数导致索引失效
        DateUtils.toDatabaseFormat(queryParams, "startTime", "endTime");
    
        // 查询数据
        Page<SpuSpecBO> boPage = this.baseMapper.listPagedSpecs(page, queryParams);
    
        // 实体转换
        return specConverter.bo2PageVo(boPage);
    }
    
    /**
     * 获取表单数据
     *
     * @param id ID
     * @return
     */
    @Override
    public SpecForm getSpecFormData(Long id) {
        Spec entity = this.getById(id);
        return specConverter.entity2Form(entity);
    }
    
    /**
     * 新增
     *
     * @param formData 表单对象
     * @return
     */
    @Override
    public boolean saveSpec(SpecForm formData) {
        // 实体转换 form->entity
        Spec entity = specConverter.form2Entity(formData);
        return this.save(entity);
    }
    
    /**
     * 更新
     *
     * @param id   ID
     * @param formData 表单对象
     * @return
     */
    @Override
    public boolean updateSpec(Long id,SpecForm formData) {
        Spec entity = specConverter.form2Entity(formData);
        return this.updateById(entity);
    }
    
    /**
     * 删除
     *
     * @param idsStr ID，多个以英文逗号(,)分割
     * @return true|false
     */
    @Override
    public boolean deleteSpecs(String idsStr) {
        Assert.isTrue(StrUtil.isNotBlank(idsStr), "删除的数据为空");
        // 逻辑删除
        List<Long> ids = Arrays.stream(idsStr.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return this.removeByIds(ids);
    }

    @Override
    public void saveSpuSpecs(Long spuId, List<SpuForm.SpuSpec> specList) {
        // 如果规格列表为空，则删除所有旧规格
        if (specList == null || specList.isEmpty()) {
            this.remove(new LambdaQueryWrapper<Spec>().eq(Spec::getSpuId, spuId));
        } else {
            // 获取当前数据库中的规格
            Map<Long, Spec> existingSpecs = this.list(new LambdaQueryWrapper<Spec>().eq(Spec::getSpuId, spuId))
                    .stream().collect(Collectors.toMap(Spec::getId, Function.identity()));

            List<Spec> specsToSave = new ArrayList<>();
            for (int i = 0; i < specList.size(); i++) {
                Spec newSpec = specConverter.formSpec2Entity(specList.get(i));
                newSpec.setSort(i + 1);
                newSpec.setSpuId(spuId);

                // 如果存在旧规格则移除，这样existingSpecs中剩下的即为需要删除的规格
                if (newSpec.getId() != null) {
                    existingSpecs.remove(newSpec.getId());
                }

                specsToSave.add(newSpec);
            }

            // 删除不再存在的规格
            if (!existingSpecs.isEmpty()) {
                this.removeByIds(existingSpecs.keySet());
            }
        }
    }


}
