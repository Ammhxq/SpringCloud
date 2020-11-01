package com.atguigu.springclou.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springclou.myhandlr.CustomerHandler;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handleException")
    public CommonResult<Payment> getTestHot(){

        return new CommonResult<>(200,"按资源名称限流",new Payment(2020L,"serial"));
    }

    public CommonResult  handleException(BlockException e){

        return new CommonResult<>(444,e.getClass().getCanonicalName()+"\t 服务不可用");
    }

    @GetMapping("/customerBlock")
    @SentinelResource(value = "customerBlock",blockHandlerClass = CustomerHandler.class,blockHandler = "customerBlock2")
    public CommonResult<Payment> customerBlock(){

        return new CommonResult<>(200,"按资源名称限流",new Payment(2020L,"serial"));
    }

}
