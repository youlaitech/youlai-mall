package com.youlai.mall.oms.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huawei
 * @desc
 * @email huawei_code@163.com
 * @date 2021/1/21
 */
public interface TokenService {

    /**
     * 为当前用户请求生成Token
     *
     * @return token
     */
    String generateToken();

    /**
     * 校验 Token 是否正确
     * @param request 当前请求
     */
    void checkToken(HttpServletRequest request);
}
