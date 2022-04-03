package com.youlai.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.mall.pms.pojo.entity.PmsSpu;
import com.youlai.mall.pms.pojo.query.SpuPageQuery;
import com.youlai.mall.pms.pojo.vo.GoodsPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品持久层
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/2/5
 */
@Mapper
public interface PmsSpuMapper extends BaseMapper<PmsSpu> {

    /**
     * 「移动端」商品分页列表
     *
     * @param page
     * @param queryParams
     * @return
     */
    List<GoodsPageVO> listAppSpuPage(Page<GoodsPageVO> page, SpuPageQuery queryParams);

    List<PmsSpu> list(Page<PmsSpu> page, String name, Long categoryId);

}
