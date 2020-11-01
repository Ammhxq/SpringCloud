package com.atguigu.springcloud.service;
import org.springframework.web.bind.annotation.RequestParam;

public interface StorageService {


    void decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
