package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.dao.OrderDao;
import com.atguigu.springcloud.domain.Order;
import com.atguigu.springcloud.service.AccountServige;
import com.atguigu.springcloud.service.OrderService;
import com.atguigu.springcloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private AccountServige accountServige;
    @Resource
    private StorageService storageService;
    @Override
    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
    public void create(Order order) {

        log.info("==============开始创建订单");
        orderDao.create(order);

        log.info("==============订单微服务开始调用库存，扣减count");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("==============订单微服务开始调用库存，扣减end");

        log.info("==============订单微服务开始调用账户，扣减money");
        accountServige.decrease(order.getUserId(),order.getMoney());
        log.info("==============订单微服务开始调用账户，扣减money  end");

        log.info("==============修改订单状态开始");
        orderDao.update(order.getUserId(),0);
        log.info("==============修改订单状态结束");


        log.info("==============下订单结束😄");



    }
}
