package com.youlai.lab.spring.event;

/**
 * 说明描述
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/22 0022 20:38
 */

public class EventC {

    String name;

    public EventC(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EventC{" +
                "name='" + name + '\'' +
                '}';
    }
}
