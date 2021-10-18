package com.youlai.admin.service;

/**
 * 短信接口
 *
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 * @date 2021/10/6
 */
public interface ISmsService {


    /**
     * 发送短信验证码
     *
     * @param phoneNumbers 手机号
     * @return
     */
    boolean sendSmsCode(String phoneNumbers);
}
