package com.fly.cloud.admin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by XianRui on 2020-01-20 15:34
 **/
@RestController
@RequestMapping("/fly/config")
public class FlyCloudConfigController {

    @Value("${name}")
    private String name;

    @GetMapping("/hi")
    public String hi() {
        return " my name is " + name;
    }
}
