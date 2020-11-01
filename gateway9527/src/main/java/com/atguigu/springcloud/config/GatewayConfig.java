package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author angleflower
 */
@Component
public class GatewayConfig {
    @Bean
    public RouteLocator customRoute(RouteLocatorBuilder builder){
        RouteLocatorBuilder.Builder routes = builder.routes();
        routes.route("atguigu_route1",r-> r.path("/guonei").uri("http://news.baidu.com/guonei")).build();
        return routes.build();

    }
}
