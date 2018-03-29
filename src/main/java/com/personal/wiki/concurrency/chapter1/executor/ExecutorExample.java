package com.personal.wiki.concurrency.chapter1.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 多线程 - 线程执行者使用例子
 *
 * @author L.X <gugia@qq.com>
 */
public class ExecutorExample {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new ExecutorExampleTask("task1"));
        service.execute(new ExecutorExampleTask("task2"));
        service.execute(new ExecutorExampleTask("task3"));
        service.shutdown();
    }
}

class ExecutorExampleTask implements Runnable {

    private final String name;

    ExecutorExampleTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(name + "-[" + i + "]");
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
