package com.youlai.mall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.pms.mapper.PmsSkuMapper;
import com.youlai.mall.pms.pojo.domain.PmsSku;
import com.youlai.mall.pms.pojo.vo.SkuInfoVO;
import com.youlai.mall.pms.pojo.vo.SkuStockVO;
import com.youlai.mall.pms.pojo.vo.WareSkuStockVO;
import com.youlai.mall.pms.service.IPmsSkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PmsSkuServiceImpl extends ServiceImpl<PmsSkuMapper, PmsSku> implements IPmsSkuService {

    @Override
    public List<SkuInfoVO> getSkuInfoByIds(List<String> skuIds) {
        log.info("批量获取商品详情，skuIds:{}", skuIds);
        return baseMapper.getSkuInfoByIds(skuIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean lockStock(WareSkuStockVO skuStockVO) {
        log.info("订单锁定商品库存，商品信息：{}", skuStockVO.getItems());
        for (SkuStockVO item : skuStockVO.getItems()) {
            Long result = baseMapper.lockStock(item.getSkuId(), item.getNumber());
            if (result == 0) {
                log.info("商品库存锁定失败，商品id:{}，数量:{}", item.getSkuId(), item.getNumber());
                throw new BizException("商品库存锁定失败，商品id:" + item.getSkuId() + "，数量:" + item.getNumber());
            }
        }
        return true;
    }

    @Override
    public boolean releaseStock(WareSkuStockVO skuStockVO) {
        log.info("订单关闭释放商品库存，商品信息：{}", skuStockVO.getItems());
        for (SkuStockVO item : skuStockVO.getItems()) {
            Long result = baseMapper.releaseStock(item.getSkuId(), item.getNumber());
            if (result == 0) {
                log.info("商品库存释放失败，商品id:{}，数量:{}", item.getSkuId(), item.getNumber());
                throw new BizException("商品库存释放失败，商品id:" + item.getSkuId() + "，数量:" + item.getNumber());
            }
        }
        return true;
    }
}
