package com.atguigu.springclou.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimtComtroller {

    @GetMapping("/testA")
    public String getTest(){
        return "test============A";
    }

    @GetMapping("/testB")
    public String getTestB(){
        return "test============B";
    }

    @GetMapping("/testD")
    public String getTestD(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "test============B";
    }
    @GetMapping("/testE")
    public String getTestE(){
        int a=10/0;
        return "test============E";
    }

    @GetMapping("/hotKey")
    @SentinelResource(value = "hotKey",blockHandler = "dealBlock",fallback = "fallback",exceptionsToIgnore = RuntimeException.class)
    public String getTestHot(@RequestParam(value = "p1",required = false) String p1,@RequestParam(value = "p2",required = false) String p2){
        if("3".equals(p1)){
            throw new RuntimeException("runtime");
        }
        return "test============hotkey"+p1+"\t"+p2;
    }

    public String dealBlock(String p1, String p2, BlockException e){

        return "dealBlock"+p1+p2;
    }

    public String fallback(String p1,String p2,Throwable t){

        return "fallback"+p1+"\t"+p2+"\t"+t.getMessage();
    }



}
