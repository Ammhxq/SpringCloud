package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @author angleflower
 */
@Component
public class PaymentFallbackServiceImpl implements PaymentHysteixService {
    @Override
    public String payment(Integer id) {
        return "PaymentFallbackServiceImpl fall payment OK"+id;
    }

    @Override
    public String paymentTime(Integer id) {
        return "PaymentFallbackServiceImpl  fall paymentTime timeout"+id;
    }
}
