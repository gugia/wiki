package com.personal.wiki.concurrency.chapter2.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 多线程 - 延时阻塞队列例子
 *
 * @author L.X <gugia@qq.com>
 */
public class DelayQueueExample {

    public static void main(String[] args) throws InterruptedException {
        /* DelayQueue通过PriorityQueue，使得超时的对象最先被处理，将take对象的操作阻塞住，
        避免了遍历方式的轮询，提高了性能。在很多需要回收超时对象的场景都能用上。 */
        DelayQueue<DelayElement> queue = new DelayQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.put(new DelayElement(1000 * i, "DelayElement" + i));
        }
        while (!queue.isEmpty()) {
            DelayElement delayElement = queue.take();
            System.out.println(delayElement.getName());
        }
    }

    static class DelayElement implements Delayed {

        private final long delay;
        private final long expired;
        private final String name;

        DelayElement(int delay, String name) {
            this.delay = delay;
            this.name = name;
            expired = System.currentTimeMillis() + delay;
        }

        public String getName() {
            return name;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expired - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            long d = (getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
            return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
        }
    }
}
