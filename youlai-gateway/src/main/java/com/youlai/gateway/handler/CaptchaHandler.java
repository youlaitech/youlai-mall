package com.youlai.gateway.handler;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import cn.hutool.core.util.IdUtil;
import com.youlai.common.constant.SecurityConstants;
import com.youlai.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 验证码处理器
 *
 * @author haoxr
 * @since 2.4.1
 */
@Component
@RequiredArgsConstructor
public class CaptchaHandler implements HandlerFunction<ServerResponse> {

    private final StringRedisTemplate redisTemplate;

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {

        // 获取验证码
        GifCaptcha captcha = CaptchaUtil.createGifCaptcha(120, 40, 4); // 宽、高、位数
        String captchaCode = captcha.getCode(); // 验证码
        String captchaImgBase64 = captcha.getImageBase64Data(); // 验证码图片Base64

        // 验证码文本缓存至Redis，用于登录校验
        String verifyCodeKey = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(SecurityConstants.VERIFY_CODE_KEY_PREFIX + verifyCodeKey, captchaCode,
                120, TimeUnit.SECONDS);

        Map<String, String> result = new HashMap<>(2);
        result.put("verifyCodeKey", verifyCodeKey);
        result.put("captchaImgBase64", captchaImgBase64);

        return ServerResponse.ok().body(BodyInserters.fromValue(Result.success(result)));
    }
}
