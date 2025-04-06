package com.youlai.common.sms.service.impl;

import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.youlai.common.sms.config.AliyunSmsProperties;
import com.youlai.common.sms.enums.SmsTypeEnum;
import com.youlai.common.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 阿里云短信业务类
 *
 * @author Ray
 * @since 2024/8/17
 */
@Service
@RequiredArgsConstructor
public class AliyunSmsService implements SmsService {

    private final AliyunSmsProperties aliyunSmsProperties;

    /**
     * 发送短信验证码
     *
     * @param mobile         手机号 13388886666
     * @param smsType        短信模板 SMS_194640010
     * @param templateParams 模板参数 [{"code":"123456"}]
     * @return boolean 是否发送成功
     */
    @Override
    public boolean sendSms(String mobile, SmsTypeEnum smsType, Map<String, String> templateParams) {

        String templateCode = aliyunSmsProperties.getTemplates().get(smsType.getValue());

        DefaultProfile profile = DefaultProfile.getProfile(aliyunSmsProperties.getRegionId(),
                aliyunSmsProperties.getAccessKeyId(), aliyunSmsProperties.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        // 创建通用的请求对象
        CommonRequest request = new CommonRequest();
        // 指定请求方式
        request.setSysMethod(MethodType.POST);
        // 短信api的请求地址(固定)
        request.setSysDomain(aliyunSmsProperties.getDomain());
        // 签名算法版(固定)
        request.setSysVersion("2017-05-25");
        // 请求 API 的名称(固定)
        request.setSysAction("SendSms");
        // 指定地域名称
        request.putQueryParameter("RegionId", aliyunSmsProperties.getRegionId());
        // 要给哪个手机号发送短信  指定手机号
        request.putQueryParameter("PhoneNumbers", mobile);
        // 您的申请签名
        request.putQueryParameter("SignName", aliyunSmsProperties.getSignName());
        // 您申请的模板 code
        request.putQueryParameter("TemplateCode", templateCode);

        request.putQueryParameter("TemplateParam", JSONUtil.toJsonStr(templateParams));

        try {
            CommonResponse response = client.getCommonResponse(request);
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }


}
