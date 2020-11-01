package com.atguigu.ruler;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author angleflower
 */
@Configuration
public class MyRule {

    @Bean
    public IRule getRule(){
        return new RandomRule();
    }

}
