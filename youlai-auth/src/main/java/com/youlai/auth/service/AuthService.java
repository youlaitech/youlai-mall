package com.youlai.auth.service;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.youlai.auth.config.CaptchaProperties;
import com.youlai.auth.model.CaptchaResult;
import com.youlai.common.constant.RedisConstants;
import com.youlai.common.sms.config.AliyunSmsProperties;
import com.youlai.common.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    private final CaptchaProperties captchaProperties;
    private final CaptchaService captchaService;

    private final AliyunSmsProperties aliyunSmsProperties;
    private final SmsService smsService;

    private final StringRedisTemplate redisTemplate;


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
     * 发送登录短信验证码
     *
     * @param mobile 手机号
     * @return true|false 是否发送成功
     */
    public boolean sendLoginSmsCode(String mobile) {
        // 获取短信模板代码
        String templateCode = aliyunSmsProperties.getTemplateCodes().get("login");

        // 生成随机4位数验证码
        String code = RandomUtil.randomNumbers(4);

        // 短信模板: 您的验证码：${code}，该验证码5分钟内有效，请勿泄漏于他人。
        // 其中 ${code} 是模板参数，使用时需要替换为实际值。
        String templateParams = JSONUtil.toJsonStr(Collections.singletonMap("code", code));

        boolean result = smsService.sendSms(mobile, templateCode, templateParams);
        if (result) {
            // 将验证码存入redis，有效期5分钟
            redisTemplate.opsForValue().set(RedisConstants.REGISTER_SMS_CODE_PREFIX + mobile, code, 5, TimeUnit.MINUTES);

            // TODO 考虑记录每次发送短信的详情，如发送时间、手机号和短信内容等，以便后续审核或分析短信发送效果。
        }
        return result;
    }

}
