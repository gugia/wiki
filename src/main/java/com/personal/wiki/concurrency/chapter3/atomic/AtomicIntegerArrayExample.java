package com.personal.wiki.concurrency.chapter3.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 多线程 - 原子数组例子<br>
 * AtomicIntegerArray、AtomicLongArray和AtomicReferenceArray是原子数组，<br>
 * 数组中每个元素在改变时都可以保持原子性。
 *
 * @author L.X <gugia@qq.com>
 */
public class AtomicIntegerArrayExample {

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerArray array = new AtomicIntegerArray(5);
        array.set(0, 0);
        array.set(1, 0);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        array.getAndIncrement(0);
                    }
                    for (int j = 0; j < 10000; j++) {
                        array.getAndIncrement(1);
                    }
                }
            });
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("array[0] = " + array.get(0) + ", array[1] = " + array.get(1));
    }
}
