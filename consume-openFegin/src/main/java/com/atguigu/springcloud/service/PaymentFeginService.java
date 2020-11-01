package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("COLUD-PAYMENT-SERVICE")
public interface PaymentFeginService {

    @GetMapping("/payment/get/{id}")
     CommonResult<Payment> query(@PathVariable("id") Long id);
}
