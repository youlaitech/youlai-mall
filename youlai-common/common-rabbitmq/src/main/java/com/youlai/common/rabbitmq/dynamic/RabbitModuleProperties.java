package com.youlai.common.rabbitmq.dynamic;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/4/4 14:51
 */
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Data
public class RabbitModuleProperties {

    private List<RabbitModuleInfo> modules;

}
