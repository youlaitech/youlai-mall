package com.youlai.gateway.handler;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.GifCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
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

        MathGenerator mathGenerator=new MathGenerator(1);
        CircleCaptcha circleCaptcha =new CircleCaptcha(150,25,4,3);
        circleCaptcha.setGenerator(mathGenerator);
        String captchaCode = circleCaptcha.getCode(); // 验证码
        String captchaBase64 = circleCaptcha.getImageBase64Data(); // 验证码图片Base64

        // 验证码文本缓存至Redis，用于登录校验
        String verifyCodeKey = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(SecurityConstants.VERIFY_CODE_CACHE_KEY_PREFIX + verifyCodeKey, captchaCode,
                120, TimeUnit.SECONDS);

        Map<String, String> result = new HashMap<>(2);
        result.put("verifyCodeKey", verifyCodeKey);
        result.put("verifyCodeBase64", captchaBase64);

        return ServerResponse.ok().body(BodyInserters.fromValue(Result.success(result)));
    }
}
