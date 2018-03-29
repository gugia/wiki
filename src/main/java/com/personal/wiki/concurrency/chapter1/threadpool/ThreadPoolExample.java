package com.personal.wiki.concurrency.chapter1.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 多线程 - 线程池例子1
 *
 * @author L.X <gugia@qq.com>
 */
public class ThreadPoolExample {

    public static void main(String[] args) throws InterruptedException {
        /* 单线程池：newSingleThreadExecutor()方法创建，五个参数分别是
        ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue())。
        含义是池中保持一个线程，最多也只有一个线程，也就是说这个线程池是顺序执行任务的，多余的任务就在队列中排队。 */
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

        /* 固定线程池：newFixedThreadPool(nThreads)方法创建，五个参数分别是
        ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue())。
        含义是池中保持nThreads个线程，最多也只有nThreads个线程，多余的任务也在队列中排队。 */
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);

        /* 缓存线程池：newCachedThreadPool()创建，五个参数分别是
        ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue())。
        含义是池中不保持固定数量的线程，随需创建，最多可以创建Integer.MAX_VALUE个线程（说一句，这个数量已经大大超过
        目前任何操作系统允许的线程数了），空闲的线程最多保持60秒，多余的任务在SynchronousQueue中等待。 */
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        System.out.println("SingleThreadPool running");
        for (int i = 0; i < 3; i++) {
            singleThreadPool.execute(new TaskInPool(i));
        }
        singleThreadPool.shutdown();
        singleThreadPool.awaitTermination(5, TimeUnit.MINUTES);
        System.out.println("FixedThreadPool running");
        for (int i = 0; i < 3; i++) {
            fixedThreadPool.execute(new TaskInPool(i));
        }
        fixedThreadPool.shutdown();
        fixedThreadPool.awaitTermination(5, TimeUnit.MINUTES);
        System.out.println("CachedThreadPool running");
        for (int i = 0; i < 3; i++) {
            cachedThreadPool.execute(new TaskInPool(i));
        }
        cachedThreadPool.shutdown();
    }
}

class TaskInPool implements Runnable {

    private final int id;

    TaskInPool(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("TaskInPool-[" + id + "] is running phase-" + i);
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("TaskInPool-[" + id + "] is over");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
