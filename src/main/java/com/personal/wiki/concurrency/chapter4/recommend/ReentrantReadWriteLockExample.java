package com.personal.wiki.concurrency.chapter4.recommend;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 多线程 - 读写锁例子<br>
 * 假设一个班级有一块黑板（共享资源），有多个老师都需要在黑板上书写。老师在写之前，先要获取写锁（writeLock），<br>
 * 在写的时候其他的老师和学生都不能在黑板上写或者读，老师写完之后，释放写锁。学生在读黑板之前，<br>
 * 先要获取读锁（readLock），多个学生可以同时获取读锁，因此多个学生可以同时读取黑板上的内容，<br>
 * 在读的时候其他老师不能在黑板上写，学生读完之后，释放读锁。直至所有学生都释放完读锁，才能够由老师获取写锁。
 *
 * @author L.X <gugia@qq.com>
 */
public class ReentrantReadWriteLockExample {

    public static Random rand = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        StringBuilder blackboard = new StringBuilder(100);

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new Teacher(lock, "Chinese Teacher", blackboard));
        service.execute(new Teacher(lock, "English Teacher", blackboard));
        service.execute(new Student(lock, "Wang", blackboard));
        service.execute(new Student(lock, "Li", blackboard));
        service.execute(new Student(lock, "Zhang", blackboard));
        service.shutdown();
    }

    private static class Teacher extends Thread {

        final private ReentrantReadWriteLock lock;
        final private String name;
        final private StringBuilder blackboard;
        private AtomicInteger id = new AtomicInteger(0);

        private Teacher(ReentrantReadWriteLock lock, String name, StringBuilder blackboard) {
            this.lock = lock;
            this.name = name;
            this.blackboard = blackboard;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    lock.writeLock().lock();
                    System.out.println(name + " get write lock");
                    blackboard.delete(0, 99).append(name)
                            .append(" has written on the blackboard, number = ")
                            .append(id.getAndIncrement());
                    System.out.println(name + " write content:" + blackboard);
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                } finally {
                    lock.writeLock().unlock();
                    System.out.println(name + " release write lock");
                }
            }
        }
    }

    private static class Student extends Thread {

        final private ReentrantReadWriteLock lock;
        final private String name;
        final private StringBuilder blackboard;

        private Student(ReentrantReadWriteLock lock, String name, StringBuilder blackboard) {
            this.lock = lock;
            this.name = name;
            this.blackboard = blackboard;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    lock.readLock().lock();
                    System.out.println(name + " get read lock");
                    System.out.println(name + " read the blackboard:" + blackboard);
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                } finally {
                    lock.readLock().unlock();
                    System.out.println(name + " release the read lock");
                }
            }
        }
    }
}
