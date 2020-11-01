package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.IMessagerProvider;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

@EnableBinding(Source.class)
public class MessagerProviderImpl implements IMessagerProvider {

    @Resource
    private MessageChannel output;
    @Override
    public String send() {

        String s = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(s).build());
        System.out.println("**********serial"+s);
        return s;
    }
}
