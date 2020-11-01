package com.atguigu.springcloud.Ib;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author angleflower
 */
@Component
@Slf4j
public class MyLb implements LoadBalanceMy {

    private AtomicInteger atomicInteger =new AtomicInteger(0);

    public final int getAndIncrement(){
        int current;
        int next;
        do {
            current=this.atomicInteger.get();
            next=current >= Integer.MAX_VALUE ? 0 :current +1;
        } while (!this.atomicInteger.compareAndSet(current,next));
        log.info("*******next:"+next);
        return next;
    }
    @Override
    public ServiceInstance instances(List<ServiceInstance> instances) {
        int index = getAndIncrement() %instances.size();
        return instances.get(index);
    }
}
