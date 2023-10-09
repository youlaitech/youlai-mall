package com.youlai.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.pms.model.entity.PmsSpu;
import com.youlai.mall.pms.model.query.SpuPageQuery;
import com.youlai.mall.pms.model.vo.PmsSpuPageVO;
import com.youlai.mall.pms.model.vo.SpuPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PmsSpuMapper extends BaseMapper<PmsSpu> {

    /**
     * Admin- 商品分页列表
     *
     * @param page
     * @param queryParams
     * @return
     */
    List<PmsSpuPageVO> getSpuPage(Page<PmsSpuPageVO> page, SpuPageQuery queryParams);

    /**
     * 「应用端」商品分页列表
     *
     * @param page
     * @param queryParams
     * @return
     */
    List<SpuPageVO> getSpuPageForApp(Page<SpuPageVO> page, SpuPageQuery queryParams);


}
