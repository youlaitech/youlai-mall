package com.youlai.mall.pms.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.common.web.exception.BizException;
import com.youlai.mall.pms.common.constant.RedisConstants;
import com.youlai.mall.pms.mapper.PmsInventoryMapper;
import com.youlai.mall.pms.pojo.domain.PmsInventory;
import com.youlai.mall.pms.pojo.dto.InventoryDTO;
import com.youlai.mall.pms.pojo.dto.InventoryNumDTO;
import com.youlai.mall.pms.service.IPmsInventoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PmsInventoryServiceImpl extends ServiceImpl<PmsInventoryMapper, PmsInventory> implements IPmsInventoryService {


    private RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean lockInventory(List<InventoryNumDTO> inventories) {
        log.info("锁定库存: {}", inventories);

        inventories.forEach(item -> {
            boolean result = this.update(new LambdaUpdateWrapper<PmsInventory>()
                    .eq(PmsInventory::getId, item.getInventoryId())
                    .apply("inventory >= locked_inventory + {0}", item.getNum())
                    .setSql("locked_inventory = locked_inventory + " + item.getNum())
            );
            if (!result) {
                throw new BizException("锁定库存失败，库存ID:" + item.getInventoryId() + "，数量:" + item.getNum());
            }
        });

        return true;
    }

    @Override
    public boolean unlockInventory(List<InventoryNumDTO> inventories) {
        log.info("释放库存:{}", inventories);

        inventories.forEach(item -> {
            boolean result = this.update(new LambdaUpdateWrapper<PmsInventory>()
                    .eq(PmsInventory::getId, item.getInventoryId())
                    .setSql("locked_inventory = locked_inventory - " + item.getNum())
            );
            if (!result) {
                throw new BizException("解锁库存失败，库存ID:" + item.getInventoryId() + "，数量:" + item.getNum());
            }
        });
        return true;
    }


    /**
     * Cache-Aside pattern 缓存、数据库读写模式
     * 1. 读取数据，先读缓存，没有就去读数据库，然后将结果写入缓存
     * 2. 写入数据，先更新数据库，再删除缓存
     *
     * @param id 库存ID
     * @return
     */
    @Override
    public Integer getInventoryById(Long id) {
        Integer inventory = 0;
        // 读->缓存
        Object cacheVal = redisTemplate.opsForValue().get(RedisConstants.PRODUCT_INVENTORY_PREFIX + id);
        if (cacheVal != null) {
            inventory = Convert.toInt(cacheVal);
            return inventory;
        }

        // 读->数据库
        PmsInventory pmsInventory = this.getOne(new LambdaQueryWrapper<PmsInventory>()
                .eq(PmsInventory::getId, id)
                .select(PmsInventory::getInventory));

        if (pmsInventory != null) {
            inventory = pmsInventory.getInventory();
            // 写->缓存
            redisTemplate.opsForValue().set(RedisConstants.PRODUCT_INVENTORY_PREFIX + id, inventory);
        }

        return inventory;

    }

    @Override
    public List<InventoryDTO> listByInventoryIds(String ids) {
        return this.listByInventoryIds(ids);
    }
}
