package com.youlai.mall.oms.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.youlai.common.web.exception.BizException;
import com.youlai.common.web.util.WebUtils;
import com.youlai.mall.oms.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.youlai.common.redis.constant.RedisKeyConstants.REDIS_KEY_TIME_OUT;
import static com.youlai.common.redis.constant.RedisKeyConstants.TOKEN_VERIFY;

/**
 * @author huawei
 * @desc Token 服务
 * @email huawei_code@163.com
 * @date 2021/1/21
 */
@Service
@AllArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN = "token";

    private RedisTemplate redisTemplate;

    @Override
    public String generateToken() {
        StringBuffer sb = new StringBuffer();
        String userId = WebUtils.getUserId().toString();
        //生成UUID
        String token = IdUtil.randomUUID();
        //前缀 + 用户id + UUID组成 token
        String key = sb.append(TOKEN_VERIFY).append(userId).append(':').append(token).toString();
        //将token写入redis并设置过期时间
        redisTemplate.opsForValue().set(key, key, REDIS_KEY_TIME_OUT);
        //redis写入成功则设置token成功
        return token;
    }

    @Override
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN);
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(TOKEN);
            if (StringUtils.isBlank(token)) {
                throw new BizException("请勿重复提交");
            }
        }

        StringBuilder sb = new StringBuilder();
        String userId = WebUtils.getUserId().toString();
        String key = sb.append(TOKEN_VERIFY).append(userId).append(':').append(token).toString();

        // 2、校验token是否存在
        if (!StrUtil.equals(redisTemplate.opsForValue().get(key).toString(), key)) {
            log.debug("请求：{} 使用 token：{} 校验幂等性，缓存中不存在", request.getRequestURI(), token);
            throw new BizException("请勿重复提交");
        }
        // 3、校验token是否删除成功
        if (!redisTemplate.delete(key)) {
            log.debug("请求：{} 使用 token：{} 校验幂等性，删除缓存数据失败", request.getRequestURI(), token);
            throw new BizException("请勿重复提交");
        }
    }
}
