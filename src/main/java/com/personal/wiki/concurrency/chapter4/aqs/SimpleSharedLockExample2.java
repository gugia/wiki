package com.personal.wiki.concurrency.chapter4.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 多线程 - 正确的共享锁示范例子<br>
 * 一次唤醒所有阻塞线程的共享锁，也是一次性的锁，用完之后，由于tryReleaseShared不能将state的值重新设置为其他值，<br>
 * 因此以后该锁就失效了。这个锁其实就是简化版的CountDownLatch。
 *
 * @author L.X <gugia@qq.com>
 */
public class SimpleSharedLockExample2 {

    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected int tryAcquireShared(int arg) {
            return getState() == 0 ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (;;) {
                if (compareAndSetState(1, 0)) {
                    return true;
                }
            }
        }

        protected Sync() {
            setState(1);
        }
    }

    private final Sync sync = new Sync();

    public void lock() {
        sync.acquireShared(1);
    }

    public void unlock() {
        sync.releaseShared(1);
    }

    private static class MyThread extends Thread {

        private final String name;
        private final SimpleSharedLockExample2 lock;

        private MyThread(String name, SimpleSharedLockExample2 lock) {
            this.name = name;
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                System.out.println(name + " begin to get lock");
                lock.lock();
                System.out.println(name + " get the lock");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        final SimpleSharedLockExample2 lock = new SimpleSharedLockExample2();
        MyThread t1 = new MyThread("t1", lock);
        MyThread t2 = new MyThread("t2", lock);
        MyThread t3 = new MyThread("t3", lock);
        t1.start();
        t2.start();
        t3.start();
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("main thread invoke unlock ");
            lock.unlock();
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("main thread exit!");
    }
}
