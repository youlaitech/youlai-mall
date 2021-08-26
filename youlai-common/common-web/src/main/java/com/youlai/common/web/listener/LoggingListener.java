package com.youlai.common.web.listener;

import org.slf4j.MDC;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.core.Ordered;

/**
 * @author <a href="mailto:xianrui0365@163.com">xianrui</a>
 * @date 2021/8/26
 */
public class LoggingListener implements SmartApplicationListener,Ordered {

        public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
            return ApplicationEnvironmentPreparedEvent.class.isAssignableFrom(eventType) || ApplicationPreparedEvent.class.isAssignableFrom(eventType);
        }

        public void onApplicationEvent(ApplicationEvent event) {
            if (event instanceof ApplicationEnvironmentPreparedEvent) {
                String appName = ((ApplicationEnvironmentPreparedEvent) event).getEnvironment().getProperty("spring.application.name");
                MDC.put("appName",appName);
            }

            if (event instanceof ApplicationPreparedEvent) {
            }
        }
        @Override
        public int getOrder() {
            // 写在加载配置文件之后
            return Ordered.HIGHEST_PRECEDENCE -1;
        }

}
