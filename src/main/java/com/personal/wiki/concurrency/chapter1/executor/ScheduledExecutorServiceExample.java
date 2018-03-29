package com.personal.wiki.concurrency.chapter1.executor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 多线程 - 重复执行和延期执行例子
 *
 * @author L.X <gugia@qq.com>
 */
public class ScheduledExecutorServiceExample {

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(2);
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("beep");
            }
        }, 1, 1, TimeUnit.SECONDS);

        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("cancel beep");
                scheduledFuture.cancel(true);
                scheduler.shutdown();
            }
        }, 10, TimeUnit.SECONDS);
    }
}
