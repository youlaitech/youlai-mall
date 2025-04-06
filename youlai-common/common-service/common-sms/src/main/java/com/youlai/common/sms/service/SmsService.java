package com.youlai.common.sms.service;


import com.youlai.common.sms.enums.SmsTypeEnum;

import java.util.Map;

/**
 * 短信服务接口层
 *
 * @author Ray.Hao
 * @since 2024/8/17
 */
public interface SmsService {

    /**
     * 发送短信
     *
     * @param mobile         手机号 13388886666
     * @param smsType        短信模板 SMS_194640010，模板内容：您的验证码为：${code}，请在5分钟内使用
     * @param templateParams 模板参数 [{"code":"123456"}] ，用于替换短信模板中的变量
     * @return boolean 是否发送成功
     */
    boolean sendSms(String mobile, SmsTypeEnum smsType, Map<String, String> templateParams);
}
