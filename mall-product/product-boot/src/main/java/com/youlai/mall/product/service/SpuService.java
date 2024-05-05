package com.youlai.mall.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.product.model.entity.Spu;
import com.youlai.mall.product.model.form.SpuForm;
import com.youlai.mall.product.model.query.SpuPageQuery;
import com.youlai.mall.product.model.vo.*;

import java.util.List;

/**
 * SPU 接口
 *
 * @author haoxr
 * @since  2022/2/5
 */
public interface SpuService extends IService<Spu> {


    /**
     * Admin-商品分页列表
     *
     * @param queryParams
     * @return
     */
    IPage<PmsSpuPageVO> listPagedSpu(SpuPageQuery queryParams);

    /**
     * 「应用端」商品分页列表
     *
     * @param queryParams
     * @return
     */
    IPage<SpuPageVO> listPagedSpuForApp(SpuPageQuery queryParams);


    /**
     * 「应用端」获取商品详情
     *
     * @param spuId
     * @return
     */
    SpuDetailVO getSpuDetailForApp(Long spuId);


    /**
     * 保存商品
     *
     * @param formData 商品表单
     * @return
     */
    boolean saveSpu(SpuForm formData);

    /**
     * 获取商品表单数据
     *
     * @param spuId SPU ID
     * @return
     */
    SpuForm getSpuForm(Long spuId);


    /**
     * 删除商品
     *
     * @param ids 商品ID，多个以英文逗号(,)分割
     * @return
     */
    boolean removeBySpuIds(String ids);

    /**
     * 获取秒杀商品列表
     * TODO
     *
     * @return
     */
    List<SeckillingSpuVO> listSeckillingSpu();


}
