package com.fly.system.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by XianRui on 2020-02-27 18:25
 **/
@RestController
@RequestMapping("/system")
@RefreshScope
public class SystemController {

    @Value("${system.info.title}")
    private String title;

    @GetMapping("/title")
    public String getTitle(){
        return title;
    }

}
