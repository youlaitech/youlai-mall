package com.youlai.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.pms.pojo.entity.PmsSpu;
import com.youlai.mall.pms.pojo.query.SpuPageQuery;
import com.youlai.mall.pms.pojo.vo.PmsSpuPageVO;
import com.youlai.mall.pms.pojo.vo.SpuPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PmsSpuMapper extends BaseMapper<PmsSpu> {

    /**
     * 「管理端」 商品分页列表
     *
     * @param page
     * @param queryParams
     * @return
     */
    List<PmsSpuPageVO> listPmsSpuPages(Page<PmsSpuPageVO> page, SpuPageQuery queryParams);

    /**
     * 「应用端」商品分页列表
     *
     * @param page
     * @param queryParams
     * @return
     */
    List<SpuPageVO> listSpuPages(Page<SpuPageVO> page, SpuPageQuery queryParams);


}
