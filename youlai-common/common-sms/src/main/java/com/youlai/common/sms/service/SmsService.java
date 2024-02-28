package com.youlai.common.sms.service;


/**
 * 短信服务接口层
 *
 * @author haoxr
 * @since 3.0.0
 */
public interface SmsService {

    /**
     * 发送短信验证码
     *
     * @param mobile   手机号 13388886666
     * @param templateCode  短信模板 SMS_194640010
     * @param templateParam 模板参数 "[{"code":"123456"}]"
     *
     * @return  boolean 是否发送成功
     */
    boolean sendSms(String mobile, String templateCode, String templateParam);


}
