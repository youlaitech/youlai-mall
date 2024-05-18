package com.youlai.mall.product.service.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.product.mapper.SpuMapper;
import com.youlai.mall.product.model.entity.Spu;
import com.youlai.mall.product.model.query.SpuPageQuery;
import com.youlai.mall.product.model.vo.SeckillingSpuVO;
import com.youlai.mall.product.model.vo.SpuDetailVO;
import com.youlai.mall.product.model.vo.SpuPageVO;
import com.youlai.mall.product.service.app.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品服务实现类
 *
 * @author haoxr
 * @since 2024/5/17
 */
@Service
public class ProductServiceImpl extends ServiceImpl<SpuMapper, Spu> implements ProductService {

    /**
     * APP-商品分页列表
     *
     * @param queryParams 查询参数
     * @return 商品分页列表 IPage<SpuPageVO>
     */
    @Override
    public IPage<SpuPageVO> listPagedSpuForApp(SpuPageQuery queryParams) {
        Page<SpuPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<SpuPageVO> list = this.baseMapper.listPagedSpuForApp(page, queryParams);
        page.setRecords(list);
        return page;
    }

    /**
     * App-获取商品详情
     *
     * @param spuId 商品ID
     * @return 商品详情
     */
    @Override
    public SpuDetailVO getSpuDetailForApp(Long spuId) {

        SpuDetailVO spuDetailVO = new SpuDetailVO();

        return spuDetailVO;
    }


    /**
     * 获取商品秒杀接口
     *
     * @return 商品秒杀列表
     */
    @Override
    public List<SeckillingSpuVO> listSeckillingSpu() {
        List<Spu> entities = this.list(new LambdaQueryWrapper<Spu>()
                .select(Spu::getId, Spu::getName, Spu::getImgUrl)
                .orderByDesc(Spu::getCreateTime)
        );
        return spuConverter.entity2SeckillingVO(entities);
    }


}
