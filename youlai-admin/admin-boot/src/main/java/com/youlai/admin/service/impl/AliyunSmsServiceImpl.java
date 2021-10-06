package com.youlai.admin.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.youlai.admin.service.ISmsService;
import com.youlai.common.constant.AuthConstants;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 阿里云短信服务
 *
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 * @date 2021/10/6
 */
@Service
@ConfigurationProperties(prefix = "aliyun.sms")
@RequiredArgsConstructor
public class AliyunSmsServiceImpl implements ISmsService {


    @Setter
    private String accessKeyId;

    @Setter
    private String accessKeySecret;

    @Setter
    private String domain;

    @Setter
    private String regionId;

    @Setter
    private String templateCode;

    @Setter
    private String signName;


    private final StringRedisTemplate stringRedisTemplate;


    /**
     * 发送短信
     *
     * @param phoneNumber 手机号
     * @return
     */
    @Override
    public boolean sendSmsCode(String phoneNumber) {
        String code = RandomUtil.randomNumbers(6); // 随机生成6位的手机验证码
        stringRedisTemplate.opsForValue().set(AuthConstants.SMS_CODE_PREFIX + phoneNumber, code, 600, TimeUnit.SECONDS);
        boolean result = this.sendSms(phoneNumber, code);
        return result;
    }

    private boolean sendSms(String phoneNumbers, String code) {
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
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
