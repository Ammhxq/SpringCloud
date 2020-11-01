package com.atguigu.springcloud.domin.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Author: hexingquan
 * @Date: 2020/11/1 6:24 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class User {

    private String name;

    private int age;
}
