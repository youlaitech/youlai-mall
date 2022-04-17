package com.youlai.lab.spring;

import com.youlai.lab.spring.event.*;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * spring事件监听机制
 * 添加监听器 参考:{@link org.springframework.context.event.EventListenerMethodProcessor#processBean}
 * 初始化事件监听: {@link org.springframework.context.support.AbstractApplicationContext#initApplicationEventMulticaster}
 * 推断事件类型: {@link org.springframework.context.event.SimpleApplicationEventMulticaster#resolveDefaultEventType}
 * 根据事件类型获取对应的监听器:{@link org.springframework.context.event.AbstractApplicationEventMulticaster#getApplicationListeners}
 * 执行监听逻辑: {@link org.springframework.context.event.SimpleApplicationEventMulticaster#invokeListener}
 *
 *
 * 推断事件类型:
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/22 0022 20:18
 */
public class EventTests {
    /**
     * 发布一个事件
     *
     */
    @Test
   void publisherA(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EventListener.class);
        context.publishEvent(new EventA("hello event"));
    }


    /**
     * 发布一个用注解接收的事件
     */
    @Test
    void publisherB(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ListenerB.class);
        context.publishEvent(new EventB("注解事件"));
    }

    /**
     * 在发布一个事件,多个监听的情况下
     * 用Order注解对监听事件排序,值越小越先执行
     */
    @Test
    void publisherC(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ListenerC.class);
        context.publishEvent(new EventC("事件排序"));
    }

    /**
     * 自定义事件分发器
     */
    @Test
    void publicsherD(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.registerBean("applicationEventMulticaster",EventMulticasterD.class);
        context.refresh();
        context.publishEvent(new EventD("自定义事件分发器"));
    }
}
