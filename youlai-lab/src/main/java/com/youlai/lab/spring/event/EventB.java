package com.youlai.lab.spring.event;

/**
 * 说明描述
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/22 0022 20:26
 */
public class EventB {
    String name;

    public EventB(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EventB{" +
                "name='" + name + '\'' +
                '}';
    }
}
