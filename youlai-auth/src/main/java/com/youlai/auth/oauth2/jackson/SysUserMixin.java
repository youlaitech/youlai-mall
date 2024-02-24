package com.youlai.auth.oauth2.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * SysUserDetails 反序列化注册
 *
 * 刷新模式根据 refresh_token 从 oauth2_authorization 表中获取字段 attributes 内容反序列化成
 *
 * @author haoxr
 * @see org.springframework.security.jackson2.UserMixin
 * @since 2023/7/4
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
@JsonDeserialize(using = SysUserDeserializer.class)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysUserMixin {
}
