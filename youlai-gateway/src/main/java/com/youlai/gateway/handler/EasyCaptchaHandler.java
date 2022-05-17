package com.youlai.gateway.handler;

import cn.hutool.core.util.IdUtil;
import com.wf.captcha.base.Captcha;
import com.youlai.common.constant.SecurityConstants;
import com.youlai.common.result.Result;
import com.youlai.gateway.component.EasyCaptchaProducer;
import com.youlai.gateway.enums.CodeTypeEnum;
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
 * @date 2021/10/4
 */
@Component
@RequiredArgsConstructor
public class EasyCaptchaHandler implements HandlerFunction<ServerResponse> {

    private final EasyCaptchaProducer easyCaptchaProducer;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {

        CodeTypeEnum codeTypeEnum = CodeTypeEnum.ARITHMETIC;
        Captcha captcha = easyCaptchaProducer.getCaptcha(codeTypeEnum);
        String captchaValue = captcha.text();
        // 对于数学类型的需要进行处理
        if (codeTypeEnum == null || codeTypeEnum == CodeTypeEnum.ARITHMETIC) {
            if (captcha.getCharType() - 1 == CodeTypeEnum.ARITHMETIC.ordinal() && captchaValue.contains(".")) {
                captchaValue = captchaValue.split("\\.")[0];
            }
        }

        // 缓存验证码至Redis
        String uuid = IdUtil.simpleUUID();
        stringRedisTemplate.opsForValue().set(SecurityConstants.VALIDATION_CODE_KEY_PREFIX + uuid, captchaValue, 60, TimeUnit.SECONDS);

        // 获取到验证码Base64编码字符串
        String captchaBase64 = captcha.toBase64();
        Map<String, String> result = new HashMap<>(2);
        result.put("uuid", uuid);
        result.put("img", captchaBase64);

        return ServerResponse.ok().body(BodyInserters.fromValue(Result.success(result)));
    }
}
