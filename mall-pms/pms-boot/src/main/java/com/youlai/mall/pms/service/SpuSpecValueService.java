package com.youlai.mall.pms.service;

import com.youlai.mall.pms.model.entity.SpuSpecValue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.model.form.SpecValueForm;
import com.youlai.mall.pms.model.query.SpecValuePageQuery;
import com.youlai.mall.pms.model.vo.SpecValuePageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
/**
 *  服务类
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
public interface SpuSpecValueService extends IService<SpuSpecValue> {


    /**
     *分页列表
     *
     * @return
     */
    IPage<SpecValuePageVO> listPagedSpecValues(SpecValuePageQuery queryParams);


    /**
     * 获取表单数据
     *
     * @param id ID
     * @return
     */
     SpecValueForm getSpecValueFormData(Long id);


    /**
     * 新增
     *
     * @param formData 表单对象
     * @return
     */
    boolean saveSpecValue(SpecValueForm formData);

    /**
     * 修改
     *
     * @param id   ID
     * @param formData 表单对象
     * @return
     */
    boolean updateSpecValue(Long id, SpecValueForm formData);


    /**
     * 删除
     *
     * @param idsStr ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteSpecValues(String idsStr);

}
