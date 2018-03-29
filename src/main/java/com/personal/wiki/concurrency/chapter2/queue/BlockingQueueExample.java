package com.personal.wiki.concurrency.chapter2.queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程 - 阻塞队列生产者和消费者例子
 *
 * @author L.X <gugia@qq.com>
 */
public class BlockingQueueExample {

    public static void main(String[] args) throws InterruptedException {
        /* LinkedBlockingQueue和ArrayedBlockingQueue
        BlockingQueue是所有阻塞队列的父接口，具体的实现类有LinkedBlockingQueue、ArrayedBlockingQueue、
        SynchronousQueue、PriorityBlockingQueue和DelayQueue。 LinkedBlockingQueue和ArrayedBlockingQueue的共同点在于，
        它们都是一个FIFO（先入先出）队列，列头元素就是最老的元素，列尾元素就是最新的元素。元素总是从列尾插入队列，
        从列头取出队列。它们的不同之处在于，LinkedBlockingQueue的内部实现是一个链表，而ArrayedBlockingQueue的内部实现是
        一个原生数组。正如其他Java集合一样，链表形式的队列，其存取效率要比数组形式的队列高。但是在一些并发程序中，数组形式
        的队列由于具有一定的可预测性，因此可以在某些场景中获得更好的效率。另一个不同点在于，ArrayedBlockingQueue支持“公平”
        策略。若在构造函数中指定了“公平”策略为true，则阻塞在插入或取出方法的所有线程也会按照FIFO进行排队，这样可以有效避免
        一些线程被“饿死”。总体而言，LinkedBlockingQueue是阻塞队列的最经典实现，在不需要“公平”策略时，基本上使用它就够了。
         */
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(3);
        //BlockingQueue<String> blockingQueue = new ArrayedBlockingQueue<>(3);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            service.submit(new Producer("Producer " + i, blockingQueue));
        }
        for (int i = 0; i < 5; i++) {
            service.submit(new Consumer("Consumer " + i, blockingQueue));
        }
        service.shutdown();
    }
}

class Producer implements Runnable {

    private final String name;
    private final BlockingQueue<String> blockingQueue;
    private static Random rand = new Random();
    private static AtomicInteger productID = new AtomicInteger(0);

    public Producer(String name, BlockingQueue<String> blockingQueue) {
        this.name = name;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(rand.nextInt(5));
                String str = "Product " + productID.getAndIncrement();
                blockingQueue.add(str);
                /* 注意，这里得到的size()有可能是错误的 */
                System.out.println(name + " product " + str + ", queue size = " + blockingQueue.size());
            }
            System.out.println(name + " is over");
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

class Consumer implements Runnable {

    private final String name;
    private final BlockingQueue<String> blockingQueue;
    private static Random rand = new Random();

    Consumer(String name, BlockingQueue<String> blockingQueue) {
        this.name = name;
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(rand.nextInt(5));
                String str = blockingQueue.take();
                /* 注意，这里得到的size()有可能是错误的 */
                System.out.println(name + " consume " + str + ", queue size = " + blockingQueue.size());
            }
            System.out.println(name + " is over");
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
