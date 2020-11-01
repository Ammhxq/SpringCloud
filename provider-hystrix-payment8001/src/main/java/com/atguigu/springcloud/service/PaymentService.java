package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.commons.util.IdUtils;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public String getResult(int id){
        return Thread.currentThread().getName()+"getResultOK"+"\t"+id ;
    }
    @HystrixCommand(fallbackMethod = "getResultCommod",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "2000")
    })
    public String getResultTimeOut(int id){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Thread.currentThread().getName()+"getResultTimeOut"+"\t"+id ;
    }

    public String getResultCommod(int id){
        return "fallbackmethod"+"\t"+Thread.currentThread().getName()+"\t"+id;
    }

    @HystrixCommand(fallbackMethod = "paymentBreakerFullback",commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "60")//失败率达到多少熔断
    })
    public String paymentBreaker(int id){
        if(id<0){
            throw new RuntimeException("id不能小于0");
        }
        return Thread.currentThread().getName()+"\t"+"调用成功"+id;

    }

    public String paymentBreakerFullback(int id){
        return "id should not less 0"+id;
    }
}
