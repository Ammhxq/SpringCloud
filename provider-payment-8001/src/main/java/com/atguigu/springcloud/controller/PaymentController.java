package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import com.netflix.discovery.DiscoveryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * @author angleflower
 */
@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @PostMapping("/payment/create")
    public CommonResult<Integer> create(@RequestBody Payment payment){
        int i = paymentService.create(payment);
        log.info("插入一条数据"+i);
        if(i>0){
            return new CommonResult<Integer>(200,"创建成功"+port,i);
        }
        return new CommonResult<>(444,"创建失败",null);
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> query(@PathVariable("id") Long id){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Payment payment = paymentService.selectById(id);
        log.info("查询一条数据"+payment+"hahh+++++++++++++++++");
        if(payment !=null){
            return new CommonResult<Payment>(200,"查询数据成功"+port,payment);
        }
        return new CommonResult<>(444,"没有对应数据"+id,null);
    }
    @GetMapping("/payment/lb")
    public String getPort(){
        return port;
    }

    @GetMapping("/payment/zipkin")
    public String getPortRes(){
        return "zipkin  ========================";
    }

}
