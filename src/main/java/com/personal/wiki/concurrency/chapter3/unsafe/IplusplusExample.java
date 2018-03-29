package com.personal.wiki.concurrency.chapter3.unsafe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 多线程 - i++非线程安全例子
 *
 * @author L.X <gugia@qq.com>
 */
public class IplusplusExample {

    private volatile static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int j = 0; j < 10; j++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    for (int k = 0; k < 10000; k++) {
                        i++;
                    }
                }
            });
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
        System.out.println(i);
    }
}
