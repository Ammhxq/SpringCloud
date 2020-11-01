package com.atguigu.springcloud.dao;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author angleflower
 */
@Mapper
public interface PaymentDao {
    public int create(Payment payment);

    public Payment selectById(@Param("id") Long id);
}
