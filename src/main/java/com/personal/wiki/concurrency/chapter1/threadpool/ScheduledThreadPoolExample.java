package com.personal.wiki.concurrency.chapter1.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 多线程 - 线程池例子2
 *
 * @author L.X <gugia@qq.com>
 */
public class ScheduledThreadPoolExample {

    public static void main(String[] args) throws InterruptedException {
        /* 单线程调度线程池：newSingleThreadScheduledExecutor()创建，五个参数分别是 
        ThreadPoolExecutor(1, Integer.MAX_VALUE, 0, NANOSECONDS, new DelayedWorkQueue())。
        含义是池中保持1个线程，多余的任务在DelayedWorkQueue中等待。 */
        ScheduledExecutorService singleThreadScheduledPool = Executors.newSingleThreadScheduledExecutor();

        /* 固定调度线程池：newScheduledThreadPool(n)创建，五个参数分别是 
        ThreadPoolExecutor(n, Integer.MAX_VALUE, 0, NANOSECONDS, new DelayedWorkQueue())。
        含义是池中保持n个线程，多余的任务在DelayedWorkQueue中等待。 */
        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(5);

        for (int i = 0; i < 3; i++) {
            singleThreadScheduledPool.schedule(new TaskInScheduledPool(i), 0, TimeUnit.SECONDS);
        }
        singleThreadScheduledPool.shutdown();
        singleThreadScheduledPool.awaitTermination(1, TimeUnit.MINUTES);
        for (int i = 0; i < 3; i++) {
            scheduledPool.schedule(new TaskInScheduledPool(i), 0, TimeUnit.SECONDS);
        }
        scheduledPool.shutdown();
    }
}

class TaskInScheduledPool implements Runnable {

    private final int id;

    TaskInScheduledPool(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("TaskInScheduledPool-[" + id + "] is running phase-" + i);
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("TaskInScheduledPool-[" + id + "] is over");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
