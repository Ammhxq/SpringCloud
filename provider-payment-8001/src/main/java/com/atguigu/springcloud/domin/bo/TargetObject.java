package com.atguigu.springcloud.domin.bo;

/**
 * @Author: hexingquan
 * @Date: 2021/1/18 2:37 下午
 */
public class TargetObject {
    private String value;

    public TargetObject() {
        value = "JavaGuide";
    }

    public void publicMethod(String s) {
        System.out.println("I love " + s);
    }

    private void privateMethod() {
        System.out.println("value is " + value);
    }
}
