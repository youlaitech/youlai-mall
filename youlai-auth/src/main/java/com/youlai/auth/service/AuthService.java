package com.youlai.auth.service;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.core.util.IdUtil;
import com.youlai.auth.config.CaptchaProperties;
import com.youlai.auth.model.CaptchaResult;
import com.youlai.auth.util.SecurityUtils;
import com.youlai.common.constant.RedisConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务
 *
 * @author Ray Hao
 * @since 3.1.0
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final CaptchaService captchaService;
    private final RedisTemplate<String, String> redisTemplate;
    private final CaptchaProperties captchaProperties;

    /**
     * 获取图形验证码
     *
     * @return Result<CaptchaResult>
     */
    public CaptchaResult getCaptcha() {

        AbstractCaptcha captcha = captchaService.generate();

        // 验证码文本缓存至Redis，用于登录校验
        String captchaId = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(
                RedisConstants.CAPTCHA_CODE_PREFIX + captchaId,
                captcha.getCode(),
                captchaProperties.getExpireSeconds(),
                TimeUnit.SECONDS
        );

        CaptchaResult captchaResult = CaptchaResult.builder()
                .captchaId(captchaId)
                .captchaBase64(captcha.getImageBase64Data())
                .build();



        return captchaResult;
    }

    /**
     * 注销登出
     *
     * @return Result
     */
    public boolean logout() {
        String jti = SecurityUtils.getJti();
        Optional<Long> expireTimeOpt = Optional.ofNullable(SecurityUtils.getExp());

        long currentTimeInSeconds = System.currentTimeMillis() / 1000; // 当前时间（单位：秒）

        expireTimeOpt.ifPresent(expireTime -> {
            if (expireTime > currentTimeInSeconds) {
                // token未过期，添加至缓存作为黑名单，缓存时间为token剩余的有效时间
                long remainingTimeInSeconds = expireTime - currentTimeInSeconds;
                redisTemplate.opsForValue().set(RedisConstants.TOKEN_BLACKLIST_PREFIX + jti, "", remainingTimeInSeconds, TimeUnit.SECONDS);
            }
        });

        if (expireTimeOpt.isEmpty()) {
            // token 永不过期则永久加入黑名单
            redisTemplate.opsForValue().set(RedisConstants.TOKEN_BLACKLIST_PREFIX + jti, "");
        }

        return true;
    }
}
