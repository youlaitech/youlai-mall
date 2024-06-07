package com.youlai.mall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.constant.RedisConstants;
import com.youlai.mall.product.converter.SkuConverter;
import com.youlai.mall.product.mapper.SkuMapper;
import com.youlai.mall.product.model.bo.SkuBO;
import com.youlai.mall.product.model.dto.LockSkuDTO;
import com.youlai.mall.product.model.dto.SkuDTO;
import com.youlai.mall.product.model.entity.Sku;
import com.youlai.mall.product.model.entity.SkuAttributeValue;
import com.youlai.mall.product.model.form.SpuForm;
import com.youlai.mall.product.service.SkuService;
import com.youlai.mall.product.service.SkuAttributeValueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * SKU 服务实现类
 *
 * @author Ray Hao
 * @since 2022/12/21
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SkuConverter skuConverter;
    private final SkuAttributeValueService skuAttributeValueService;

    /**
     * 获取 SKU 信息
     *
     * @param skuId SKU ID
     * @return 商品库存信息
     */
    @Override
    public SkuDTO getSkuInfo(Long skuId) {
        SkuBO bo = this.getSkuById(skuId);
        return skuConverter.convertToDto(bo);
    }

    /**
     * 获取 SKU 信息
     *
     * @param skuId SKU ID
     * @return SKU 信息
     */
    public SkuBO getSkuById(Long skuId) {
        return this.baseMapper.getSkuById(skuId);
    }

    /**
     * 获取 SKU 列表
     *
     * @param skuIds SKU ID 列表
     * @return SKU 列表
     */
    @Override
    public List<SkuDTO> listSkusByIds(List<Long> skuIds) {
        List<SkuBO> list = this.baseMapper.listSkusByIds(skuIds);
        return skuConverter.convertToDto(list);
    }

    /**
     * 校验并锁定库存
     *
     * @param orderToken  订单临时编号 (此时订单未创建)
     * @param lockSkuList 锁定商品库存列表
     * @return true/false
     */
    @Override
    @Transactional
    public boolean lockStock(String orderToken, List<LockSkuDTO> lockSkuList) {
        log.info("订单({})锁定商品库存：{}", orderToken, JSONUtil.toJsonStr(lockSkuList));
        Assert.isTrue(CollectionUtil.isNotEmpty(lockSkuList), "订单({})未包含任何商品", orderToken);

        // 校验库存数量是否足够以及锁定库存
        for (LockSkuDTO lockedSku : lockSkuList) {
            Integer quantity = lockedSku.getQuantity(); // 订单的商品数量
            // 库存足够
            boolean lockResult = this.update(new LambdaUpdateWrapper<Sku>()
                    .setSql("locked_stock = locked_stock + " + quantity) // 修改锁定商品数
                    .eq(Sku::getId, lockedSku.getSkuId())
                    .apply("stock - locked_stock >= {0}", quantity) // 剩余商品数 ≥ 订单商品数
            );
            Assert.isTrue(lockResult, "商品库存不足");
        }

        // 锁定的商品缓存至 Redis (后续使用：1.取消订单解锁库存；2：支付订单扣减库存)
        redisTemplate.opsForValue().set(RedisConstants.PRODUCT_SKUS_LOCK_PREFIX + orderToken, lockSkuList);
        return true;
    }

    /**
     * 解锁库存
     * <p>
     * 订单超时未支付，释放锁定的商品库存
     *
     * @param orderSn 订单号
     * @return true/false
     */
    @Override
    @Transactional
    public boolean unlockStock(String orderSn) {
        List<LockSkuDTO> lockedSkus = (List<LockSkuDTO>) redisTemplate.opsForValue().get(RedisConstants.PRODUCT_SKUS_LOCK_PREFIX + orderSn);
        log.info("释放订单({})锁定的商品库存:{}", orderSn, JSONUtil.toJsonStr(lockedSkus));

        // 库存已释放
        if (CollectionUtil.isEmpty(lockedSkus)) {
            return true;
        }

        // 解锁商品库存
        for (LockSkuDTO lockedSku : lockedSkus) {
            boolean unlockResult = this.update(new LambdaUpdateWrapper<Sku>()
                    .setSql("locked_stock = locked_stock - " + lockedSku.getQuantity())
                    .eq(Sku::getId, lockedSku.getSkuId())
            );
            Assert.isTrue(unlockResult, "解锁商品库存失败");
        }
        // 移除 redis 订单锁定的商品
        redisTemplate.delete(RedisConstants.PRODUCT_SKUS_LOCK_PREFIX + orderSn);
        return true;
    }

    /**
     * 扣减库存
     * <p>
     * 订单支付扣减商品库存和释放锁定库存
     *
     * @param orderSn 订单编号
     * @return ture/false
     */
    @Override
    @Transactional
    public boolean deductStock(String orderSn) {
        // 获取订单提交时锁定的商品
        List<LockSkuDTO> lockedSkus = (List<LockSkuDTO>) redisTemplate.opsForValue().get(RedisConstants.PRODUCT_SKUS_LOCK_PREFIX + orderSn);
        log.info("订单({})支付成功，扣减订单商品库存：{}", orderSn, JSONUtil.toJsonStr(lockedSkus));

        Assert.isTrue(CollectionUtil.isNotEmpty(lockedSkus), "扣减商品库存失败：订单({})未包含商品");

        for (LockSkuDTO lockedSku : lockedSkus) {
            boolean deductResult = this.update(new LambdaUpdateWrapper<Sku>()
                    .setSql("stock = stock - " + lockedSku.getQuantity())
                    .setSql("locked_stock = locked_stock - " + lockedSku.getQuantity())
                    .eq(Sku::getId, lockedSku.getSkuId())
            );
            Assert.isTrue(deductResult, "扣减商品库存失败");
        }

        // 移除订单锁定的商品
        redisTemplate.delete(RedisConstants.PRODUCT_SKUS_LOCK_PREFIX + orderSn);
        return true;
    }


    /**
     * 保存商品SKU
     *
     * @param spuId   SPU ID
     * @param skuList SKU 列表 非空的
     */
    @Override
    @Transactional
    public void saveSkus(Long spuId, List<SpuForm.Sku> skuList) {
        // 检索数据库中与spuId相关联的所有SKU
        List<Sku> existingSkusInDb = this.list(new LambdaQueryWrapper<Sku>().eq(Sku::getSpuId, spuId));

        // 从提交的表单中提取所有非空的SKU ID
        List<Long> submittedSkuIds = skuList.stream()
                .map(SpuForm.Sku::getId)
                .filter(Objects::nonNull)
                .toList();

        // 确定需要删除的SKU：如果它们在提交的SKU列表中不存在
        List<Sku> skusToDelete = existingSkusInDb.stream()
                .filter(sku -> !submittedSkuIds.contains(sku.getId()))
                .toList();

        // 如果有SKU需要删除，则进行删除操作
        if (!skusToDelete.isEmpty()) {
            List<Long> skuIdsToDelete = skusToDelete.stream()
                    .map(Sku::getId)
                    .toList();

            // 删除SKU记录
            boolean result = this.removeByIds(skuIdsToDelete);

            // 删除关联的SKU规格值
            if (result) {
                skuAttributeValueService.remove(new LambdaQueryWrapper<SkuAttributeValue>().in(SkuAttributeValue::getSkuId, skuIdsToDelete));
            }
        }

        // 循环处理提交的每个SKU，执行更新或保存操作
        for (SpuForm.Sku skuForm : skuList) {
            Sku entity = skuConverter.convertToEntity(skuForm);
            entity.setSpuId(spuId);

            // 保存或更新SKU实体
            boolean result = this.saveOrUpdate(entity);

            // 如果成功保存或更新，则处理SKU的规格值
            if (result) {
                skuAttributeValueService.saveSaleAttributeValues(entity.getId(), skuForm.getSaleAttributeValues());
            }
        }
    }

    /**
     * 根据SPU ID查询商品SKU列表
     *
     * @param spuId SPU ID
     * @return 商品SKU列表
     */
    @Override
    public List<SkuBO> listSkusBySpuId(Long spuId) {
        return this.baseMapper.listSkusBySpuId(spuId);
    }
}
