package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private DiscoveryClient discoveryClient;

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
        Payment payment = paymentService.selectById(id);
        log.info("查询一条数据"+payment+"hahh+++++++++++++++++");
        if(payment !=null){
            return new CommonResult<Payment>(200,"查询数据成功"+port,payment);
        }
        return new CommonResult<>(444,"没有对应数据"+id,null);
    }

    @GetMapping("/payment/discover")
    public Object getDiscover(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("******"+service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("COLUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
             log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;
    }
    @GetMapping("/payment/lb")
    public String getPort(){
        return port;
    }

}
