package com.atguigu.springcloud.Ib;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalanceMy {
    ServiceInstance instances(List<ServiceInstance> instances);
}
