package com.youlai.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.system.converter.ClientConverter;
import com.youlai.system.mapper.SysOauthClientMapper;
import com.youlai.system.pojo.entity.SysOauthClient;
import com.youlai.system.pojo.query.ClientPageQuery;
import com.youlai.system.pojo.vo.client.ClientPageVO;
import com.youlai.system.service.SysOauthClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * OAuth2客户端业务实现类
 *
 * @author haoxr
 * @date 2020-11-06
 */
@Service
@RequiredArgsConstructor
public class SysOauthClientServiceImpl extends ServiceImpl<SysOauthClientMapper, SysOauthClient> implements SysOauthClientService {

    private final StringRedisTemplate stringRedisTemplate;
    private final ClientConverter clientConverter;

    /**
     * 客户端分页列表
     *
     * @param queryParams
     * @return
     */
    @Override
    public IPage<ClientPageVO> listClientPages(ClientPageQuery queryParams) {

        // 参数构建
        int pageNum = queryParams.getPageNum();
        int pageSize = queryParams.getPageSize();
        String keywords = queryParams.getKeywords();

        Page<SysOauthClient> page = new Page<>(pageNum, pageSize);

        // 查询数据
        Page<SysOauthClient> entityPage = this.page(page,
                new LambdaQueryWrapper<SysOauthClient>()
                .like(StrUtil.isNotBlank(keywords), SysOauthClient::getClientId,keywords)
        );

        // 实体转换
        Page<ClientPageVO> voPage = clientConverter.entity2PageVO(entityPage);

        return voPage;
    }

    /**
     * 清理客户端缓存
     */
    @Override
    public void cleanCache() {
        Set<String> keys = stringRedisTemplate.keys("auth:oauth-client:*");
        if (CollectionUtil.isNotEmpty(keys)) {
            stringRedisTemplate.delete(keys);
        }
    }
}
