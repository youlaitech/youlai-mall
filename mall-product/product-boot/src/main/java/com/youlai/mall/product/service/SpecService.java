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
 * @author Ray.Hao
 * @since 2024-06-13
 */
public interface SpecService extends IService<Spec> {

    /**
     * 获取规格表单数据
     *
     * @param id 规格ID
     */
    SpecForm getSpecFormData(Long id);

    /**
     * 新增规格
     *
     * @param formData 表单对象
     */
    boolean saveSpec(SpecForm formData);

    /**
     * 修改规格
     *
     * @param id  规格ID
     * @param formData 规格表单对象
     */
    boolean updateSpec(Long id, SpecForm formData);

    /**
     * 删除规格
     *
     * @param ids ID，多个以英文逗号(,)分割
     */
    boolean deleteSpecs(String ids);

    /**
     * 获取分类下的规格列表
     *
     * @param categoryId 分类ID
     * @return 规格列表
     */
    List<SpecVO> getCategorySpecs(Long categoryId);
}
