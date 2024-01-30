package com.youlai.common.apidoc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * API 信息属性
 *
 * @author haoxr
 * @since 2023/10/30
 */
@Data
@ConfigurationProperties(prefix = "springdoc.info")
public class ApiDocInfoProperties {

    /**
     * API文档标题
     */
    private String title;

    /**
     * API文档版本
     */
    private String version;

    /**
     * API文档描述
     */
    private String description;

    /**
     * 联系人信息
     */
    private Contact contact;

    /**
     * 许可证信息
     */
    private License license;

    @Data
    public static class Contact {
        /**
         * 联系人姓名
         */
        private String name;
        /**
         * 联系人主页
         */
        private String url;
        /**
         * 联系人邮箱
         */
        private String email;

    }

    /**
     * 许可证信息
     */
    @Data
    public static  class  License{
        /**
         * 许可证名称
         */
        private String name;
        /**
         * 许可证URL
         */
        private String url;
    }

}
