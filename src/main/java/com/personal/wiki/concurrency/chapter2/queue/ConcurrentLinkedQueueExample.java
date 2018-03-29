package com.personal.wiki.concurrency.chapter2.queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 多线程 - 并发链接队列例子<br>
 * 非并发类例如LinkedList在多线程环境下运行是会出错的，结果的最后一行输出了队列的size值，只有它的size值不等于0，<br>
 * 这说明在多线程运行时许多poll操作并没有弹出元素，甚至很多offer操作也没有能够正确插入元素。<br>
 * 其他三种并发类都能够在多线程环境下正确运行。
 *
 * @author L.X <gugia@qq.com>
 */
public class ConcurrentLinkedQueueExample {

    private static final int TEST_INT = 10000000;

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        Queue<Integer> queue = null;
        if (args.length < 1) {
            System.out.println("Usage: input 1~4 ");
            System.exit(1);
        }
        int param = Integer.parseInt(args[0]);
        switch (param) {
            case 1:
                queue = new LinkedList<>();
                break;
            case 2:
                queue = new LinkedBlockingQueue<>();
                break;
            case 3:
                queue = new ArrayBlockingQueue<>(TEST_INT * 5);
                break;
            case 4:
                queue = new ConcurrentLinkedQueue<>();
                break;
            default:
                System.out.println("Usage: input 1~4 ");
                System.exit(2);
        }
        System.out.println("Using " + queue.getClass().getSimpleName());
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            service.submit(new Putter(queue, "Putter" + i));
        }
        TimeUnit.SECONDS.sleep(2);
        for (int i = 0; i < 5; i++) {
            service.submit(new Getter(queue, "Getter" + i));
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
        long end = System.currentTimeMillis();
        System.out.println("Time span = " + (end - start));
        System.out.println("queue size = " + queue.size());
    }

    static class Putter implements Runnable {

        private final Queue<Integer> queue;
        private final String name;

        Putter(Queue<Integer> queue, String name) {
            this.queue = queue;
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < TEST_INT; i++) {
                queue.offer(1);
            }
            System.out.println(name + " is over");
        }
    }

    static class Getter implements Runnable {

        private final Queue<Integer> queue;
        private final String name;

        Getter(Queue<Integer> queue, String name) {
            this.queue = queue;
            this.name = name;
        }

        @Override
        public void run() {
            int i = 0;
            while (i < TEST_INT) {
                synchronized (Getter.class) {
                    if (!queue.isEmpty()) {
                        queue.poll();
                        i++;
                    }
                }
            }
            System.out.println(name + " is over");
        }
    }
}
