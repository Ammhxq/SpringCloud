package com.atguigu.springcloud.strategy;

import com.atguigu.springcloud.domin.bo.strategy.PayConfirmInbound;
import com.atguigu.springcloud.domin.bo.strategy.PayConfirmOutbound;
import com.atguigu.springcloud.domin.bo.strategy.PayInbound;
import com.atguigu.springcloud.domin.bo.strategy.PayOutbound;

/**
 * @Author: hexingquan
 * @Date: 2020/12/23 3:16 下午
 */
public class WeChatHandle implements PayCommonHandle {
    @Override
    public PayOutbound pay(PayInbound payInbound) {
        return null;
    }

    @Override
    public PayConfirmOutbound payConfirm(PayConfirmInbound payConfirmInbound) {
        return null;
    }
}
