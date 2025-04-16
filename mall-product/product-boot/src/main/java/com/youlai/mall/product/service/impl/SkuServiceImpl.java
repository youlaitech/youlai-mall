package com.youlai.mall.product.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.constant.RedisConstants;
import com.youlai.common.core.exception.BusinessException;
import com.youlai.mall.product.converter.SkuConverter;
import com.youlai.mall.product.mapper.SkuMapper;
import com.youlai.mall.product.model.bo.SkuBO;
import com.youlai.mall.product.model.dto.LockSkuDTO;
import com.youlai.mall.product.model.dto.SkuDTO;
import com.youlai.mall.product.model.entity.SkuEntity;
import com.youlai.mall.product.model.entity.SkuSpecEntity;
import com.youlai.mall.product.model.form.SpuForm;
import com.youlai.mall.product.service.SkuService;
import com.youlai.mall.product.service.SkuSpecService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * SKU 服务实现类
 *
 * @author Ray.Hao
 * @since 2022/12/21
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SkuServiceImpl extends ServiceImpl<SkuMapper, SkuEntity> implements SkuService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SkuConverter skuConverter;
    private final SkuSpecService skuSpecService;

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
            boolean lockResult = this.update(new LambdaUpdateWrapper<SkuEntity>()
                    .setSql("locked_stock = locked_stock + " + quantity) // 修改锁定商品数
                    .eq(SkuEntity::getId, lockedSku.getSkuId())
                    .apply("stock - locked_stock >= {0}", quantity) // 剩余商品数 ≥ 订单商品数
            );
            Assert.isTrue(lockResult, "商品库存不足");
        }

        // 锁定的商品缓存至 Redis (后续使用：1.取消订单解锁库存；2：支付订单扣减库存)
        redisTemplate.opsForValue().set(RedisConstants.Product.SKU_LOCK + orderToken, lockSkuList);
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
        List<LockSkuDTO> lockedSkus = (List<LockSkuDTO>) redisTemplate.opsForValue()
                .get(RedisConstants.Product.SKU_LOCK + orderSn);
        log.info("释放订单({})锁定的商品库存:{}", orderSn, JSONUtil.toJsonStr(lockedSkus));

        // 库存已释放
        if (CollectionUtil.isEmpty(lockedSkus)) {
            return true;
        }

        // 解锁商品库存
        for (LockSkuDTO lockedSku : lockedSkus) {
            boolean unlockResult = this.update(new LambdaUpdateWrapper<SkuEntity>()
                    .setSql("locked_stock = locked_stock - " + lockedSku.getQuantity())
                    .eq(SkuEntity::getId, lockedSku.getSkuId())
            );
            Assert.isTrue(unlockResult, "解锁商品库存失败");
        }
        // 移除 redis 订单锁定的商品
        redisTemplate.delete(RedisConstants.Product.SKU_LOCK + orderSn);
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
        List<LockSkuDTO> lockedSkus = (List<LockSkuDTO>) redisTemplate.opsForValue().get(RedisConstants.Product.SKU_LOCK + orderSn);
        log.info("订单({})支付成功，扣减订单商品库存：{}", orderSn, JSONUtil.toJsonStr(lockedSkus));

        if (CollectionUtil.isEmpty(lockedSkus)) {
            throw new BusinessException("订单({})未包含任何商品", orderSn);
        }

        for (LockSkuDTO lockedSku : lockedSkus) {
            boolean deductResult = this.update(new LambdaUpdateWrapper<SkuEntity>()
                    .setSql("stock = stock - " + lockedSku.getQuantity())
                    .setSql("locked_stock = locked_stock - " + lockedSku.getQuantity())
                    .eq(SkuEntity::getId, lockedSku.getSkuId())
            );
            Assert.isTrue(deductResult, "扣减商品库存失败");
        }

        // 移除订单锁定的商品
        redisTemplate.delete(RedisConstants.Product.SKU_LOCK + orderSn);
        return true;
    }


    /**
     * 保存 SKU
     *
     * @param spuId   SPU ID
     * @param skuList SKU 列表 非空的
     */
    @Override
    @Transactional
    public void saveSkus(Long spuId, List<SpuForm.Sku> skuList) {
        // 1. 查询现有SKU ID集合
        Set<Long> existingSkuIds = this.list(new LambdaQueryWrapper<SkuEntity>()
                        .select(SkuEntity::getId)
                        .eq(SkuEntity::getSpuId, spuId))
                .stream()
                .map(SkuEntity::getId)
                .collect(Collectors.toSet());

        // 2. 提取提交的SKU ID集合
        Set<Long> submittedSkuIds = skuList.stream()
                .map(SpuForm.Sku::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // 3. 计算需要删除的SKU ID
        List<Long> skuIdsToDelete = existingSkuIds.stream()
                .filter(id -> !submittedSkuIds.contains(id))
                .toList();

        // 4. 批量删除SKU及关联规格
        if (!skuIdsToDelete.isEmpty()) {
            this.removeByIds(skuIdsToDelete);
            skuSpecService.remove(new LambdaQueryWrapper<SkuSpecEntity>()
                    .in(SkuSpecEntity::getSkuId, skuIdsToDelete));
        }

        // 5. 批量处理SKU
        for (SpuForm.Sku skuForm : skuList) {
            SkuEntity entity = skuConverter.toEntity(skuForm);
            entity.setSpuId(spuId);

            // 计算规格组合Hash值
            List<SpuForm.SpecValue> specList = skuForm.getSpecList();
            String specHash = calculateSpecHash(specList);
            entity.setSpecHash(specHash);

            // 保存或更新SKU
            this.saveOrUpdate(entity);

            // 直接处理规格数据
            skuSpecService.saveSkuSpecs(entity.getId(),specList);
        }
    }

    /**
     * 计算规格组合的Hash值
     * 规则：将规格按name排序后拼接成字符串，然后取MD5
     */
    private String calculateSpecHash(List<SpuForm.SpecValue> specList) {
        if (CollectionUtils.isEmpty(specList)) {
            return StringUtils.EMPTY;
        }

        // 1. 按规格名称排序
        List<SpuForm.SpecValue> sortedSpecs = specList.stream()
                .sorted(Comparator.comparing(SpuForm.SpecValue::getName))
                .toList();

        // 2. 拼接成字符串：格式 "name:value,name:value"
        String specString = sortedSpecs.stream()
                .map(spec -> spec.getName() + ":" + spec.getValue())
                .collect(Collectors.joining(","));

        // 3. 生成MD5（截取前16位）
        return DigestUtil.md5Hex(specString);
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
