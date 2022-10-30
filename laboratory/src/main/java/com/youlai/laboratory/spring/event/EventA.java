package com.youlai.laboratory.spring.event;

import org.springframework.context.ApplicationEvent;

/**
 * 事件A
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/22 0022 20:14
 */
public class EventA extends ApplicationEvent {
    public EventA(Object source) {
        super(source);
    }
}
