package com.youlai.mall.pms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.pms.pojo.dto.admin.GoodsFormDTO;
import com.youlai.mall.pms.pojo.entity.PmsSpu;
import com.youlai.mall.pms.pojo.query.SpuPageQuery;
import com.youlai.mall.pms.pojo.vo.AppSpuDetailVO;
import com.youlai.mall.pms.pojo.vo.AppSpuPageVO;
import com.youlai.mall.pms.pojo.vo.admin.GoodsDetailVO;

import java.util.List;

/**
 * 商品业务接口
 *
 * @author haoxr
 * @date 2022/2/5
 */
public interface IPmsSpuService extends IService<PmsSpu> {

    /**
     * 「移动端」商品分页列表
     *
     * @param queryParams
     * @return
     */
    IPage<AppSpuPageVO> listAppSpuWithPage(SpuPageQuery queryParams);


    /**
     * 「移动端」获取商品详情
     *
     * @param spuId
     * @return
     */
    AppSpuDetailVO getAppSpuDetail(Long spuId);


    /**
     *
     * @param id
     * @return
     */
    GoodsDetailVO getGoodsById(Long id);


    IPage<PmsSpu> list(Page<PmsSpu> page,  String name,Long categoryId);

    boolean addGoods(GoodsFormDTO goodsFormDTO);

    boolean removeByGoodsIds(List<Long> spuIds);

    boolean updateGoods(GoodsFormDTO goodsFormDTO);


   
}
