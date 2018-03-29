package com.personal.wiki.concurrency.chapter4.recommend;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程 - Condition例子<br>
 * Condition是与RentrantLock配合使用，来实现synchronize中的wait、notify功能的一个接口，<br>
 * 其具体实现类为AQS中的ConditionObject，使用一个ReentrantLock以及它的两个Condition实现了<br>
 * 对一个原生数组的安全并发访问，其中notFull条件用来控制数组不会溢出，notEmpty条件控制数组不会为空时还被取出元素。
 *
 * @author L.X <gugia@qq.com>
 */
public class ConditionExample {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final BoundedBuffer buffer = new BoundedBuffer();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 100; i++) {
                        String str = "string" + i;
                        buffer.put(str);
                        System.out.println(Thread.currentThread() + " put " + str);
                        TimeUnit.MILLISECONDS.sleep(50);
                    }
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 100; i++) {
                        String str = (String) buffer.take();
                        System.out.println(Thread.currentThread() + " take " + str);
                        TimeUnit.MILLISECONDS.sleep(50);
                    }
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        service.shutdown();
    }

    private static class BoundedBuffer {

        final Lock lock = new ReentrantLock();
        final Condition notFull = lock.newCondition();
        final Condition notEmpty = lock.newCondition();

        final Object[] items = new Object[100];
        int putptr, takeptr, count;

        public void put(Object x) throws InterruptedException {
            lock.lock();
            try {
                while (count == items.length) {
                    notFull.await();
                }
                items[putptr] = x;
                if (++putptr == items.length) {
                    putptr = 0;
                }
                ++count;
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }

        public Object take() throws InterruptedException {
            lock.lock();
            try {
                while (count == 0) {
                    notEmpty.await();
                }
                Object x = items[takeptr];
                if (++takeptr == items.length) {
                    takeptr = 0;
                }
                --count;
                notFull.signal();
                return x;
            } finally {
                lock.unlock();
            }
        }
    }
}
