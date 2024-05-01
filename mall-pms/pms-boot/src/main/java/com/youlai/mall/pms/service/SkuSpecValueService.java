package com.youlai.mall.pms.service;

import com.youlai.mall.pms.model.entity.SkuSpecValue;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.model.form.SkuSpecValueForm;
import com.youlai.mall.pms.model.form.SpuForm;
import com.youlai.mall.pms.model.query.SkuSpecValuePageQuery;
import com.youlai.mall.pms.model.vo.SkuSpecValuePageVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * SKU规格值 服务类
 *
 * @author Ray Hao
 * @since 2024-04-14
 */
public interface SkuSpecValueService extends IService<SkuSpecValue> {

    void saveSkuSpecValues(Long id, List<SpuForm.Attribute> specList);
}
