package com.personal.wiki.concurrency.chapter4.recommend;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程 - ReentrantLock例子<br>
 * 与synchronize关键字相比，ReentrantLock更加灵活，唯一的不足是使用时必须使用try-finally模式来进行加解锁。
 *
 * @author L.X <gugia@qq.com>
 */
public class ReentrantLockExample {

    public static void main(String[] args) {
        final ReentrantLock lock = new ReentrantLock();
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 2; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread() + " ready to get lock");
                        lock.lock();
                        System.out.println(Thread.currentThread() + " get the lock");
                        for (int j = 0; j < 5; j++) {
                            System.out.println(Thread.currentThread() + " is running, number = " + j);
                            TimeUnit.MILLISECONDS.sleep(500);
                        }
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        lock.unlock();
                        System.out.println(Thread.currentThread() + " unlock");
                    }
                }
            });
        }
        service.shutdown();
    }
}
