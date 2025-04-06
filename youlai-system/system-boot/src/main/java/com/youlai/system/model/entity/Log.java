package com.youlai.system.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.youlai.common.enums.LogModuleEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统日志 实体类
 *
 * @author Ray.Hao
 * @since 2.10.0
 */
@Data
@TableName("sys_log")
public class Log implements Serializable {

    /**
     *  主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 日志模块
     */
    private LogModuleEnum module;

    /**
     * 请求方式
     */
    @TableField(value = "request_method")
    private String requestMethod;

    /**
     * 请求参数
     */
    @TableField(value = "request_params")
    private String requestParams;

    /**
     * 响应参数
     */
    @TableField(value = "response_content")
    private String responseContent;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 请求路径
     */
    private String requestUri;

    /**
     * IP 地址
     */
    private String ip;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 浏览器版本
     */
    private String browserVersion;

    /**
     * 终端系统
     */
    private String os;

    /**
     * 执行时间(毫秒)
     */
    private Long executionTime;

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}