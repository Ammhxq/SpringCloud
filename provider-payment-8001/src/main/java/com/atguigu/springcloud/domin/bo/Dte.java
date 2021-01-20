package com.atguigu.springcloud.domin.bo;

import java.io.IOException;
import java.util.concurrent.Phaser;

/**
 * @Author: hexingquan
 * @Date: 2020/12/29 7:52 下午
 */
public class Dte {
    public static void main(String[] args) throws IOException {

        int repeats = 5;    // 指定任务最多执行的次数
        System.out.println(Integer.MAX_VALUE);

        Phaser phaser = new Phaser() {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("---------------PHASE[" + phase + "],Parties[" + registeredParties + "] ---------------");
                return phase + 1 >= repeats  || registeredParties == 0;
            }
        };

        for (int i = 0; i < 10; i++) {
            phaser.register();                      // 注册各个参与者线程
            new Thread(new Task3(phaser), "Thread-" + i).start();
        }
    }
}

class Task3 implements Runnable {
    private final Phaser phaser;

    Task3(Phaser phaser) {
        this.phaser = phaser;
    }

    @Override
    public void run() {
        while (!phaser.isTerminated()) {   //只要Phaser没有终止, 各个线程的任务就会一直执行
            int i = phaser.arriveAndAwaitAdvance();     // 等待其它参与者线程到达
            // do something
            System.out.println(Thread.currentThread().getName() + ": 执行完任务");
        }
    }
}


