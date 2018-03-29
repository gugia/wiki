package com.personal.wiki.concurrency.chapter2.queue;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

/**
 * 多线程 - 传输队列例子<br>
 * TransferQueue是一个非常重要的队列，且提供了一些阻塞队列所不具有的特性。<br>
 * 简单来说，TransferQueue提供了一个场所，生产者线程使用transfer方法传入一些对象并阻塞，<br>
 * 直至这些对象被消费者线程全部取出。前面介绍的SynchronousQueue很像一个容量为0的TransferQueue。<br>
 * 下面是一个例子，一个生产者使用transfer方法传输10个字符串，两个消费者线程则各取出5个字符串，<br>
 * 可以看到生产者在transfer时会一直阻塞直到所有字符串被取出。
 *
 * @author L.X <gugia@qq.com>
 */
public class TransferQueueExample {

    public static void main(String[] args) {
        TransferQueue<String> queue = new LinkedTransferQueue<>();
        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(new Producer("Producer1", queue));
        service.submit(new Consumer("Consumer1", queue));
        service.submit(new Consumer("Consumer2", queue));
        service.shutdown();
    }

    static class Producer implements Runnable {

        private final String name;
        private final TransferQueue<String> queue;

        Producer(String name, TransferQueue<String> queue) {
            this.name = name;
            this.queue = queue;
        }

        @Override
        public void run() {
            System.out.println("begin transfer objects");
            try {
                for (int i = 0; i < 10; i++) {
                    queue.transfer("Product" + i);
                    System.out.println(name + " transfer " + "Product" + i);
                }
                System.out.println("after transformation");
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(name + " is over");
        }
    }

    static class Consumer implements Runnable {

        private final String name;
        private final TransferQueue<String> queue;
        private static Random rand = new Random(System.currentTimeMillis());

        Consumer(String name, TransferQueue<String> queue) {
            this.name = name;
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    String str = queue.take();
                    System.out.println(name + " take " + str);
                    TimeUnit.SECONDS.sleep(rand.nextInt(5));
                }
                System.out.println(name + " is over");
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
