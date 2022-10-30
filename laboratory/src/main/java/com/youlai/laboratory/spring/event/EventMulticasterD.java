package com.youlai.laboratory.spring.event;

import org.springframework.context.event.EventListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import java.util.concurrent.Executor;

/**
 * 自定义事件分发器
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @Date 2022/3/22 0022 21:34
 */
public class EventMulticasterD extends SimpleApplicationEventMulticaster {
    @Override
    protected Executor getTaskExecutor() {
        System.out.println("获取自定义的线程池执行异步");
        return super.getTaskExecutor();
    }

    @EventListener
    public void listener(EventD eventD){
        System.out.println("接收到事件:"+eventD);
        System.out.println("处理事件");
    }
}
