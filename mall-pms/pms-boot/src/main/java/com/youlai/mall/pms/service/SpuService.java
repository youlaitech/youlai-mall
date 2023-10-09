package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.model.entity.PmsSpu;
import com.youlai.mall.pms.model.form.PmsSpuForm;
import com.youlai.mall.pms.model.query.SpuPageQuery;
import com.youlai.mall.pms.model.vo.*;

import java.util.List;

/**
 * 商品业务接口
 *
 * @author haoxr
 * @date 2022/2/5
 */
public interface SpuService extends IService<PmsSpu> {


    /**
     * Admin-商品分页列表
     *
     * @param queryParams
     * @return
     */
    IPage<PmsSpuPageVO> getSpuPage(SpuPageQuery queryParams);

    /**
     * 「应用端」商品分页列表
     *
     * @param queryParams
     * @return
     */
    IPage<SpuPageVO> getSpuPageForApp(SpuPageQuery queryParams);


    /**
     * Admin-获取商品详情
     *
     * @param id
     * @return
     */
    PmsSpuDetailVO getSpuDetail(Long id);

    /**
     * 「应用端」获取商品详情
     *
     * @param spuId
     * @return
     */
    SpuDetailVO getSpuDetailForApp(Long spuId);


    /**
     * 新增商品
     *
     * @param formData
     * @return
     */
    boolean addSpu(PmsSpuForm formData);

    /**
     * 修改商品
     *
     * @param spuId    商品ID
     * @param formData
     * @return
     */
    boolean updateSpuById(Long spuId, PmsSpuForm formData);

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
