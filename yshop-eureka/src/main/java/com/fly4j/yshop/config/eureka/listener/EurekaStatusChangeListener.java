package com.fly4j.yshop.pms.eureka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by XianRui on 2020-01-19 18:06
 **/
@Slf4j
@Component
public class EurekaStatusChangeListener {

    @EventListener
    public void listen(EurekaInstanceCanceledEvent event) {
        //服务下线事件
        log.info("service:{}|{} shutdown",event.getAppName(),event.getServerId());
    }

    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        //服务注册事件
        log.info("service:{}|{} registration successful",event.getInstanceInfo().getAppName(),event.getInstanceInfo().getIPAddr());
    }

    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        //服务续约事件
        log.info("heartbeat check :{}|{}",event.getInstanceInfo().getAppName(),event.getInstanceInfo().getIPAddr());
    }

    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        //注册中心启动事件
    }

    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        //Server启动
    }

}
