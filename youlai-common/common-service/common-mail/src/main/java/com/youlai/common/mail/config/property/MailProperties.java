package com.youlai.common.mail.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 将 application.yml 中的 spring.mail  绑定到此类中
 *
 * @author Ray.Hao
 * @since 2024/8/17
 */
@ConfigurationProperties(prefix = "spring.mail")
@Data
public class MailProperties {

    /**
     * 邮件服务器主机名或 IP 地址。
     * 例如：smtp.example.com
     */
    private String host;

    /**
     * 邮件服务器端口号。
     * 例如：587
     */
    private int port;

    /**
     * 用于连接邮件服务器的用户名。
     * 例如：your_email@example.com
     */
    private String username;

    /**
     * 用于连接邮件服务器的密码。
     * 该密码应安全存储，不应在代码中硬编码。
     */
    private String password;

    /**
     * 邮件发送者地址。
     */
    private String from;

    /**
     * 邮件服务器的其他属性配置。
     * 这些配置通常用于进一步定制邮件发送行为。
     */
    private Properties properties = new Properties();

    /**
     * 内部类，用于封装邮件服务器的详细配置。
     * 包含 SMTP 相关的配置选项。
     */
    @Data
    public static class Properties {

        /**
         * SMTP 配置选项类。
         * 包含认证、加密等与 SMTP 协议相关的配置。
         */
        private Smtp smtp = new Smtp();

        @Data
        public static class Smtp {

            /**
             * 是否启用 SMTP 认证。
             * 如果为 `true`，则需要提供有效的用户名和密码进行认证。
             */
            private boolean auth;

            /**
             * STARTTLS 加密配置选项。
             */
            private StartTls starttls = new StartTls();

            @Data
            public static class StartTls {

                /**
                 * 是否启用 STARTTLS 加密。
                 * 如果为 `true`，在发送邮件时将启用 STARTTLS 协议进行加密传输。
                 */
                private boolean enable;
            }
        }
    }
}
