package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;
import org.elasticsearch.client.indices.CreateIndexResponse;

import java.io.IOException;

/**
 * @author angleflower
 */
public interface PaymentService {
     int create(Payment payment);

    public Payment selectById( Long id);

     CreateIndexResponse createIndex() throws IOException;
}
