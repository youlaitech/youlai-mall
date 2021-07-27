package com.youlai.mall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.redis.utils.RedisUtils;
import com.youlai.mall.pms.config.ProductLocalCache;
import com.youlai.mall.pms.mapper.PmsSpuMapper;
import com.youlai.mall.pms.pojo.dto.app.ProductFormDTO;
import com.youlai.mall.pms.pojo.entity.PmsAttribute;
import com.youlai.mall.pms.pojo.entity.PmsSku;
import com.youlai.mall.pms.pojo.entity.PmsSpu;
import com.youlai.mall.pms.pojo.entity.PmsSpuAttributeValue;
import com.youlai.mall.pms.service.IPmsAttributeService;
import com.youlai.mall.pms.service.IPmsSkuService;
import com.youlai.mall.pms.service.IPmsSpuAttributeValueService;
import com.youlai.mall.pms.service.IProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.youlai.mall.pms.common.constant.PmsConstants.LOCK_PRODUCT_DETAIL;
import static com.youlai.mall.pms.common.constant.PmsConstants.PRODUCT_DETAIL_CACHE;

/**
 * @author haoxr
 * @date 2020-11-06
 */
@Service
@Slf4j
@AllArgsConstructor
public class ProductServiceImpl extends ServiceImpl<PmsSpuMapper, PmsSpu> implements IProductService {

    private final IPmsSkuService iPmsSkuService;
    private final IPmsSpuAttributeValueService iPmsSpuAttributeValueService;
    private final IPmsAttributeService iPmsSpecService;
    private final RedisUtils redisUtils;
    private final RedissonClient redissonClient;
    private final ProductLocalCache productLocalCache;


    @Override
    public ProductFormDTO getProductById(Long spuId) {
        //1、一级本地缓存设置
        ProductFormDTO product = productLocalCache.get(PRODUCT_DETAIL_CACHE + spuId);
       /* if (null != product) {
            log.info("get LocalCache product:" + product);
            return product;
        }
        //2、二级缓存设置，Redis中获取商品详情信息
        product = (ProductFormDTO) redisUtils.get(PRODUCT_DETAIL_CACHE + spuId);
        if (null != product) {
            log.info("get redis product:" + product);
            return product;
        }
        //3、分布式锁，保证原子操作
        RLock lock = redissonClient.getLock(LOCK_PRODUCT_DETAIL + spuId);
        try {
            if (lock.tryLock()) {
                // spu
                PmsSpu spu = this.getById(spuId);
                SpuDTO SpuDTO = new SpuDTO();
                BeanUtil.copyProperties(spu, SpuDTO);
                if (StrUtil.isNotBlank(spu.getAlbum())) {
                    // spu专辑图片转换处理 json字符串 -> List
                    List<String> pics = JSONUtil.toList(JSONUtil.parseArray(spu.getAlbum()), String.class);
                    SpuDTO.setPics(pics);
                }
                // 属性
                List<PmsSpuAttributeValue> attrs = iPmsSpuAttributeValueService.list(
                        new LambdaQueryWrapper<PmsSpuAttributeValue>(
                        ).eq(PmsSpuAttributeValue::getSpuId, spuId)
                );
                // 规格
              //  List<PmsSpec> specs = iPmsSpecService.listBySpuId(spuId);

                List<PmsAttribute> specs=null;
                // sku
                List<PmsSku> skuList = iPmsSkuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));

               // product = new ProductFormDTO(SpuDTO, attrs, specs, skuList);
                product = new ProductFormDTO();
                //TODO 4、需要判断商品是否是秒杀商品，根据秒杀信息更新商品秒杀相关信息
                log.info("get db product:" + product);
                redisUtils.set(PRODUCT_DETAIL_CACHE + spuId, product, 3600);
                productLocalCache.setLocalCache(PRODUCT_DETAIL_CACHE + spuId,product);
            } else {
                log.info("get redis2 product:" + product);
                product = (ProductFormDTO) redisUtils.get(PRODUCT_DETAIL_CACHE + spuId);
                if (null!=product) {
                    productLocalCache.setLocalCache(PRODUCT_DETAIL_CACHE + spuId, product);
                }
            }
        } finally {
            if (lock.isLocked()) {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }*/
        return product;
    }
}
