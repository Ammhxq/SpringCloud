package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentHysteixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "globFallback")
public class OrderHystrixController {
    @Resource
    private PaymentHysteixService paymentHysteixService;

    @GetMapping("/consumer/payment/hystix/ok/{id}")
    public String payment(@PathVariable("id") Integer id){

        return paymentHysteixService.payment(id);
    }

    public String paymentTime(@PathVariable("id") Integer id){

        return paymentHysteixService.paymentTime(id);
    }

    @GetMapping("/consumer/payment/hystix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "getResultCommod",commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "1500")
//    })
    @HystrixCommand
    public String getResultTimeOut(@PathVariable int id){
        return paymentHysteixService.paymentTime(id);
    }

    public String getResultCommod(@PathVariable int id){
        return "对方系统繁忙请稍后再试"+"\t"+Thread.currentThread().getName()+"\t"+id;
    }

    public String globFallback(){
        return "全局异常处理器";
    }
}
