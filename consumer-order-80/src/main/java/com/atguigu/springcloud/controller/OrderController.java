package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.Ib.LoadBalanceMy;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * @author angleflower
 */
@RestController
@Slf4j
public class OrderController {

    private static final String URL="http://COLUD-PAYMENT-SERVICE";
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalanceMy loadBalanceMy;
    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
         return restTemplate.postForObject(URL+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> get(@PathVariable("id")Long id){
        return restTemplate.getForObject(URL+"/payment/get/"+id,CommonResult.class);
    }

    @GetMapping("/consumer/payment/lb")
    public String getLb(){
        List<ServiceInstance> instances = discoveryClient.getInstances("COLUD-PAYMENT-SERVICE");
        if(CollectionUtils.isEmpty(instances)){
            return null;
        }
        ServiceInstance serviceInstance = loadBalanceMy.instances(instances);
        URI uri = serviceInstance.getUri();
        return restTemplate.getForObject(uri+"/payment/lb",String.class);
    }

    @GetMapping("/consumer/payment/zipkin")
    public String payment(){
        return restTemplate.getForObject("http://localhost:8001" + "/payment/zipkin/", String.class);
    }


}
