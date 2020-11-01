package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author angleflower
 */
@SpringBootApplication
@EnableFeignClients
public class OpenFeginMain80 {
    public static void main(String[] args) {

        SpringApplication.run(OpenFeginMain80.class,args);
    }
}
