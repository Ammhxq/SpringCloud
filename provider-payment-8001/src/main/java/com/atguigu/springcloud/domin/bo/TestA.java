package com.atguigu.springcloud.domin.bo;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: hexingquan
 * @Date: 2021/1/14 5:59 下午
 */
@Data
public class TestA<T extends User> {

    private T s;
    private T bad;

    public static void main(String[] args) {
        TestA<User> testA = new TestA<>();
        Object[] s ={123};
        try {
            Method printMsg = testA.getClass().getMethod("printMsg", Object[].class);
            Object invoke = printMsg.invoke(testA, s);
            System.out.println(invoke);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public <T> void printMsg(T... args) {
        for (T t : args) {
            System.out.println("泛型测试  t is " + t);
        }
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        list.forEach(System.out::println);

        List<?>[] lsa = new List<?>[10];
        Object[] oa = lsa;
        List<Integer> li = new ArrayList<>();
        li.add(3);
        oa[1] = li;
        Integer i = (Integer) lsa[1].get(0);
        System.out.println(i);
    }
}
