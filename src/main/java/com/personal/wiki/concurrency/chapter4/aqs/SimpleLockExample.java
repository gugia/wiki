package com.personal.wiki.concurrency.chapter4.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 多线程 - AQS简单排它锁实现例子
 *
 * @author L.X <gugia@qq.com>
 */
public class SimpleLockExample {

    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            return compareAndSetState(0, 1);
        }

        @Override
        protected boolean tryRelease(int arg) {
            setState(0);
            return true;
        }

        protected Sync() {
            super();
        }
    }

    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

    private static class MyThread extends Thread {

        private final String name;
        private final SimpleLockExample lock;

        private MyThread(String name, SimpleLockExample lock) {
            this.name = name;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                lock.lock();
                System.out.println(name + " get the lock");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } finally {
                lock.unlock();
                System.out.println(name + " release the lock");
            }
        }
    }

    public static void main(String[] args) {
        final SimpleLockExample mutex = new SimpleLockExample();
        MyThread t1 = new MyThread("t1", mutex);
        MyThread t2 = new MyThread("t2", mutex);
        MyThread t3 = new MyThread("t3", mutex);
        t1.start();
        t2.start();
        t3.start();
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("main thread exit!");
    }
}
