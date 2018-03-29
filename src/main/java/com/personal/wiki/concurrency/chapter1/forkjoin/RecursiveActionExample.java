package com.personal.wiki.concurrency.chapter1.forkjoin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * 多线程 - fork-join并行计算例子1
 *
 * @author L.X <gugia@qq.com>
 */
public class RecursiveActionExample {

    private final static int NUMBER = 8000000;

    public static void main(String[] args) throws InterruptedException {
        double[] array1 = new double[NUMBER];
        double[] array2 = new double[NUMBER];
        double[] array3 = new double[NUMBER];
        for (int i = 0; i < NUMBER; i++) {
            array1[i] = i;
            array2[i] = i;
            array3[i] = i;
        }
        System.out.println("本机处理器核心数 = " + Runtime.getRuntime().availableProcessors());
        /* 并行计算 */
        long startTime = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new ComputeTask(array1, 0, array1.length));
        long endTime = System.currentTimeMillis();
        System.out.println("Fork-join time span = " + (endTime - startTime));
        /* 单线程计算 */
        startTime = System.currentTimeMillis();
        for (int i = 0; i < NUMBER; i++) {
            array2[i] = Math.sin(array2[i]) + Math.cos(array2[i]) + Math.tan(array2[i]);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Single thread time span = " + (endTime - startTime));
        /* 四线程计算 */
        startTime = System.currentTimeMillis();
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.execute(new ArrayTask(array3, 0, NUMBER / 4));
        service.execute(new ArrayTask(array3, NUMBER / 4, NUMBER / 2));
        service.execute(new ArrayTask(array3, NUMBER / 2, NUMBER * 3 / 4));
        service.execute(new ArrayTask(array3, NUMBER * 3 / 4, NUMBER));
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
        endTime = System.currentTimeMillis();
        System.out.println("4 thread time span = " + (endTime - startTime));
    }
}

class ComputeTask extends RecursiveAction {

    final double[] array;
    final int lo, hi;

    ComputeTask(double[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    protected void compute() {
        if (hi - lo < 2) {
            for (int i = lo; i < hi; ++i) {
                array[i] = Math.sin(array[i]) + Math.cos(array[i]) + Math.tan(array[i]);
            }
        } else {
            int mid = (lo + hi) >>> 1;
            invokeAll(new ComputeTask(array, lo, mid), new ComputeTask(array, mid, hi));
        }
    }
}

class ArrayTask implements Runnable {

    final double[] array;
    final int lo, hi;

    ArrayTask(double[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    public void run() {
        for (int i = lo; i < hi; ++i) {
            array[i] = Math.sin(array[i]) + Math.cos(array[i]) + Math.tan(array[i]);
        }
    }
}
