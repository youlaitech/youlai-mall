package com.youlai.mall.product.service.impl;

import com.youlai.mall.product.model.entity.Spec;
import com.youlai.mall.product.mapper.SpecMapper;
import com.youlai.mall.product.model.vo.SpecVO;
import com.youlai.mall.product.service.SpecService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.youlai.common.util.DateUtils;
import com.youlai.mall.product.model.form.SpecForm;
import com.youlai.mall.product.model.query.SpecPageQuery;
import com.youlai.mall.product.model.bo.SpecBO;
import com.youlai.mall.product.model.vo.SpecPageVO;
import com.youlai.mall.product.converter.SpecConverter;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;

/**
 * 规格服务实现类
 *
 * @author Ray.Hao
 * @since 2024-06-13
 */
@Service
@RequiredArgsConstructor
public class SpecServiceImpl extends ServiceImpl<SpecMapper, Spec> implements SpecService {

    private final SpecConverter specConverter;

    /**
     * 获取分页列表
     *
     * @param queryParams 查询参数
     * @return {@link IPage<SpecPageVO>} 分页列表
     */
    @Override
    public IPage<SpecPageVO> getSpecPage(SpecPageQuery queryParams) {

        // 参数构建
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        Page<SpecBO> page = new Page<>(pageNum, pageSize);

        // 格式化为数据库日期格式，避免日期比较使用格式化函数导致索引失效
        DateUtils.toDatabaseFormat(queryParams, "startTime", "endTime");
        Page<SpecBO> boPage = this.baseMapper.getSpecPage(page, queryParams);
        return specConverter.toPageVo(boPage);
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
        return specConverter.toForm(entity);
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
        Spec entity = specConverter.toEntity(formData);
        return this.save(entity);
    }

    /**
     * 更新
     *
     * @param id       ID
     * @param formData 表单对象
     * @return
     */
    @Override
    public boolean updateSpec(Long id, SpecForm formData) {
        Spec entity = specConverter.toEntity(formData);
        return this.updateById(entity);
    }

    /**
     * 删除
     *
     * @param ids ID，多个以英文逗号(,)分割
     * @return true|false
     */
    @Override
    public boolean deleteSpecs(String ids) {
        Assert.isTrue(StrUtil.isNotBlank(ids), "删除的数据为空");
        // 逻辑删除
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return this.removeByIds(idList);
    }

    /**
     * 根据分类ID获取规格列表
     *
     * @param categoryId 分类ID
     * @return
     */
    @Override
    public List<SpecVO> listSpecsByCategoryId(Long categoryId) {
        return this.baseMapper.listSpecsByCategoryId(categoryId);
    }


}
