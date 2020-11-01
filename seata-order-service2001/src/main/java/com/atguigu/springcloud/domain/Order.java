package com.atguigu.springcloud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author angleflower
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;

    private Long productId;

    private Long userId;

    private Integer count;

    /***
     * 0 创建中 1 已完成
     */
    private Integer status;

    private BigDecimal money;
}
