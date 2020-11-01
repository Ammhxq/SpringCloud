package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author angleflower
 */
@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @GetMapping("/payment/hystix/ok/{id}")
    public String payment(@PathVariable("id") Integer id){
        String result = paymentService.getResult(id);
        log.info("result"+result);
        return result;
    }
    @GetMapping("/payment/hystix/timeout/{id}")
    public String paymentTime(@PathVariable("id") Integer id){
        String result = paymentService.getResultTimeOut(id);
        log.info("result"+result);
        return result;
    }

    @GetMapping("/payment/circuit/{id}")
    public String getRest(@PathVariable("id") Integer id){
        String s = paymentService.paymentBreaker(id);
        log.info("*******result"+s);
        return s;

    }
}
