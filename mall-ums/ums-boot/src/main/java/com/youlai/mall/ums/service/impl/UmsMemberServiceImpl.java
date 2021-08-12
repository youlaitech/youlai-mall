package com.youlai.mall.ums.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.mall.pms.pojo.vo.ProductHistoryVO;
import com.youlai.mall.ums.constant.UmsConstants;
import com.youlai.mall.ums.pojo.entity.UmsMember;
import com.youlai.mall.ums.mapper.UmsUserMapper;
import com.youlai.mall.ums.service.IUmsMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UmsMemberServiceImpl extends ServiceImpl<UmsUserMapper, UmsMember> implements IUmsMemberService {

    private final RedisTemplate redisTemplate;

    @Override
    public IPage<UmsMember> list(Page<UmsMember> page, UmsMember spu) {
        List<UmsMember> list = this.baseMapper.list(page, spu);
        page.setRecords(list);
        return page;
    }

    @Override
    public void addProductViewHistory(ProductHistoryVO product, Long userId) {
        if (userId != null) {
            String key = UmsConstants.USER_PRODUCT_HISTORY + userId;
            redisTemplate.opsForZSet().add(key, product, System.currentTimeMillis());
            Long size = redisTemplate.opsForZSet().size(key);
            if (size > 10) {
                redisTemplate.opsForZSet().removeRange(key, 0, size - 11);
            }
        }
    }

    @Override
    public Set<ProductHistoryVO> getProductViewHistory(Long userId) {
        return redisTemplate.opsForZSet().reverseRange(UmsConstants.USER_PRODUCT_HISTORY + userId, 0, 9);
    }
}
