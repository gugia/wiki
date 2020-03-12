package com.personal.wiki.collection;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Comparator Example
 *
 * @author L.X <gugia@qq.com>
 */
public class ComparatorExample {

    private final static Map<String, Foo> MAP = new ConcurrentHashMap<>(16);

    public static void main(String[] args) {
        Set<LocalDateTime> set = new HashSet<>(20);
        for (int i = 0; i < 10; i++) {
            LocalDateTime time = LocalDateTime.now().plusMinutes(i + 1);
            set.add(time);
        }
        set.stream()
                .max((a, b) -> a.isAfter(b) ? 1 : -1)
                .ifPresent(a -> System.out.println(a.toString()));

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Foo foo = new Foo();
            foo.setId(String.valueOf(i + 1));
            foo.setNum(random.nextInt(1000));
            MAP.put(String.valueOf(i + 1), foo);
        }

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new ExecutorExampleTask("task1"));
        service.execute(new ExecutorExampleTask("task2"));
        service.execute(new ExecutorExampleTask("task3"));
        service.shutdown();
    }

    public static int sum() {
        int sum = MAP.values().stream().mapToInt(Foo::getNum).sum();
        Random random = new Random();
        Foo foo = MAP.get(String.valueOf(random.nextInt(10) + 1));
        foo.setNum(random.nextInt(1000));
        return sum;
    }
}

@Data
class Foo {

    private String id;
    private Integer num;
}

class ExecutorExampleTask implements Runnable {

    private final String name;
    private final Random random = new Random();

    ExecutorExampleTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(2000) + 200);
                System.out.println(name + ": " + ComparatorExample.sum());
                TimeUnit.MILLISECONDS.sleep(random.nextInt(2000) + 200);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}