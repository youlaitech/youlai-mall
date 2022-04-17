package com.youlai.lab.spring.event;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;

/**
 * 说明描述
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/22 0022 20:32
 */
public class ListenerC {

    static class Event{

    }

    @EventListener
    @Order(1)
    public void listhen1(EventC eventC){
        System.out.println("接收到事件1:"+eventC);
        System.out.println("处理事件");
    }

    @EventListener
    @Order(2)
    public void listhen2(EventC eventc){
        System.out.println("接收到事件2:"+eventc);
        System.out.println("处理事件");
    }
}
