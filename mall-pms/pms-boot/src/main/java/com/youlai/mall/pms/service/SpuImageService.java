package com.youlai.mall.pms.service;

import com.youlai.mall.pms.model.entity.SpuImage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.model.form.SpuImageForm;
import com.youlai.mall.pms.model.query.SpuImagePageQuery;
import com.youlai.mall.pms.model.vo.SpuImagePageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
/**
 * 商品图片 服务类
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
public interface SpuImageService extends IService<SpuImage> {


    /**
     *商品图片分页列表
     *
     * @return
     */
    IPage<SpuImagePageVO> listPagedSpuImages(SpuImagePageQuery queryParams);


    /**
     * 获取商品图片表单数据
     *
     * @param id 商品图片ID
     * @return
     */
     SpuImageForm getSpuImageFormData(Long id);


    /**
     * 新增商品图片
     *
     * @param formData 商品图片表单对象
     * @return
     */
    boolean saveSpuImage(SpuImageForm formData);

    /**
     * 修改商品图片
     *
     * @param id   商品图片ID
     * @param formData 商品图片表单对象
     * @return
     */
    boolean updateSpuImage(Long id, SpuImageForm formData);


    /**
     * 删除商品图片
     *
     * @param idsStr 商品图片ID，多个以英文逗号(,)分割
     * @return
     */
    boolean deleteSpuImages(String idsStr);

}
