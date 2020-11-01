package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.IMessagerProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMessageController {

    @Resource
    private IMessagerProvider iMessagerProvider;

    @GetMapping("/sendMessage")
    public String send(){

        return iMessagerProvider.send();
    }
}
