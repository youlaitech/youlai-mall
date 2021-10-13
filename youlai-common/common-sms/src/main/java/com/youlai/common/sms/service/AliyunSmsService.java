package com.youlai.common.sms.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.youlai.common.constant.AuthConstants;
import com.youlai.common.sms.config.AliyunSmsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:xianrui0365@163.com">haoxianrui</a>
 * @date 2021/10/13 23:04
 */
@Service
@RequiredArgsConstructor
public class AliyunSmsService {

    private final AliyunSmsProperties aliyunSmsProperties;

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 发送短信
     *
     * @param phoneNumbers 手机号，多个用英文逗号(,)分割
     * @param code
     * @return
     */
    private boolean sendSmsCode(String phoneNumbers, String code) {
        String code = RandomUtil.randomNumbers(6); // 随机生成6位的手机验证码
        stringRedisTemplate.opsForValue().set(AuthConstants.SMS_CODE_PREFIX + phoneNumber, code, 600, TimeUnit.SECONDS);

        DefaultProfile profile = DefaultProfile.getProfile(aliyunSmsProperties.getRegionId(),
                aliyunSmsProperties.getAccessKeyId(), aliyunSmsProperties.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        // 创建通用的请求对象
        CommonRequest request = new CommonRequest();
        // 指定请求方式
        request.setSysMethod(MethodType.POST);
        // 短信api的请求地址  固定
        request.setSysDomain(domain);
        // 签名算法版本  固定
        request.setSysVersion("2017-05-25");
        // 请求 API 的名称
        request.setSysAction("SendSms");
        // 指定地域名称
        request.putQueryParameter("RegionId", regionId);
        // 要给哪个手机号发送短信  指定手机号
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        // 您的申请签名
        request.putQueryParameter("SignName", signName);
        // 您申请的模板 code
        request.putQueryParameter("TemplateCode", templateCode);

        Map<String, Object> params = new HashMap<>();
        // 这里的key就是短信模板中的 ${xxxx}
        params.put("code", code);

        request.putQueryParameter("TemplateParam", JSONUtil.toJsonStr(params));

        try {
            CommonResponse response = client.getCommonResponse(request);
            return response.getHttpResponse().isSuccess();

        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;

    }


}
