package com.atguigu.springcloud.strategy;

import com.atguigu.springcloud.domin.bo.strategy.PayConfirmInbound;
import com.atguigu.springcloud.domin.bo.strategy.PayConfirmOutbound;
import com.atguigu.springcloud.domin.bo.strategy.PayInbound;
import com.atguigu.springcloud.domin.bo.strategy.PayOutbound;

/**
 * @Author: hexingquan
 * @Date: 2020/12/23 3:07 下午
 */
public interface PayCommonHandle {

    /***
     * 支付统一入口
     * @param payInbound
     * @return
     */
    PayOutbound pay(PayInbound payInbound);

    /***
     * 支付回调统一入口
     * @param payConfirmInbound
     * @return
     */
    PayConfirmOutbound payConfirm(PayConfirmInbound payConfirmInbound);
}
