package com.personal.wiki.concurrency.chapter3.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 多线程 - 原子属性更新器例子<br>
 * AtomicIntegerFieldUpdater、AtomicLongFieldUpdater和AtomicReferenceFieldUpdater都被称为原子属性更新器。<br>
 * 这些类的应用场景是：如果已经有一个写好的类，但是随着业务场景的变化，其中某些属性在写入的时候需要保持原子性，<br>
 * 那么就可以使用以上的类来实现这种原子性，并保持类的原有接口不变。
 *
 * @author L.X <gugia@qq.com>
 */
public class AtomicIntegerFieldUpdaterExample {

    public static void main(String[] args) throws InterruptedException {
        Student student = new Student(0, "Alex Wang");
        AtomicIntegerFieldUpdater<Student> updater = AtomicIntegerFieldUpdater.newUpdater(Student.class, "id");
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        updater.getAndIncrement(student);
                    }
                }
            });
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
        System.out.println(student);
    }

    private static class Student {

        volatile int id;
        String name;

        public Student(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Student id = " + id + ",name = " + name;
        }
    }
}
