package com.atguigu.springclou.myhandlr;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;

public class CustomerHandler {

    public static CommonResult<Payment> customerBlock(BlockException e){
        return new CommonResult<>(444,"按资源名称限流 Glob  ===1");
    }
    public static CommonResult<Payment> customerBlock2(BlockException e){
        return new CommonResult<>(444,"按资源名称限流 Glob===2");
    }
}
