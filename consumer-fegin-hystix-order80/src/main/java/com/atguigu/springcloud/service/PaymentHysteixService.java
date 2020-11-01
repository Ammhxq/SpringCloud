package com.atguigu.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "COLUD-PAYMENT-HYSTRIX-SERVICE",fallback = PaymentFallbackServiceImpl.class)
public interface PaymentHysteixService {

    @GetMapping("/payment/hystix/ok/{id}")
    public String payment(@PathVariable("id") Integer id);

    @GetMapping("/payment/hystix/timeout/{id}")
    public String paymentTime(@PathVariable("id") Integer id);
}
