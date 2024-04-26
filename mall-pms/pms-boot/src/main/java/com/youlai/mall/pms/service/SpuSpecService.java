package com.youlai.mall.pms.service;

import com.youlai.mall.pms.model.entity.Spec;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.model.form.SpecForm;
import com.youlai.mall.pms.model.form.SpuForm;
import com.youlai.mall.pms.model.query.SpecPageQuery;
import com.youlai.mall.pms.model.vo.SpecPageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 服务类
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
public interface SpuSpecService extends IService<Spec> {


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
     * @param idsStr ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteSpecs(String idsStr);


    /**
     * 保存SPU规格
     *
     * @param spuId   SPU ID
     * @param specList 规格列表
     */
    void saveSpuSpecs(Long spuId, List<SpuForm.SpuSpec> specList);
}
