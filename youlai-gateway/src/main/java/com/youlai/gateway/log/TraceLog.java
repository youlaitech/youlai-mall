package com.youlai.gateway.log;

import lombok.Data;

/**
 * 请求响应日志实体对象
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/4/28 16:54
 */
@Data
public class TraceLog {

    /**
     * 请求路径
     */
    private String requestPath;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求实体
     */
    private String requestBody;

    /**
     * 响应数据
     */
    private String responseBody;

    /**
     * 请求时间
     */
    private String requestTime;

    /**
     * 响应时间
     */
    private String responseTime;

    /**
     * 执行耗时(毫秒)
     */
    private Long executeTime;


    @Override
    public String toString() {
        return "========网关请求响应日志========\n" +
                "请求路径:" + requestPath + '\n' +
                "请求方法:" + requestMethod + '\n' +
                "请求参数:" + requestBody + '\n' +
                "响应数据:" + responseBody + '\n' +
                "请求时间:" + requestTime + '\n' +
                "响应时间:" + responseTime + '\n' +
                "执行耗时:" + executeTime + "毫秒";
    }
}
