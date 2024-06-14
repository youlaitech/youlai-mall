package com.youlai.mall.product.service;

import com.youlai.mall.product.model.entity.Spec;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.form.SpecForm;
import com.youlai.mall.product.model.query.SpecPageQuery;
import com.youlai.mall.product.model.vo.SpecPageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.mall.product.model.vo.SpecVO;

import java.util.List;

/**
 * 规格接口层
 *
 * @author Ray Hao
 * @since 2024-06-13
 */
public interface SpecService extends IService<Spec> {


    /**
     * 分页列表
     *
     * @return
     */
    IPage<SpecPageVO> listPagedSpecs(SpecPageQuery queryParams);


    /**
     * 获取表单数据
     *
     * @param id ID
     * @return
     */
    SpecForm getSpecFormData(Long id);


    /**
     * 新增
     *
     * @param formData 表单对象
     * @return
     */
    boolean saveSpec(SpecForm formData);

    /**
     * 修改
     *
     * @param id       ID
     * @param formData 表单对象
     * @return
     */
    boolean updateSpec(Long id, SpecForm formData);


    /**
     * 删除
     *
     * @param ids ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteSpecs(String ids);

    /**
     * 根据分类ID获取规格列表
     *
     * @param categoryId 分类ID
     * @return
     */
    List<SpecVO> listSpecsByCategoryId(Long categoryId);
}
