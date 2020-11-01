package com.atguigu.springcloud.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author angleflower
 */
@RestController
public class PaymentController {

    @Value("${server.port}")
    private String port;


    @GetMapping("/payment/zk")
    public String query(){
        return "zookeeper"+port+"\t"+ UUID.randomUUID().toString();
    }

}
