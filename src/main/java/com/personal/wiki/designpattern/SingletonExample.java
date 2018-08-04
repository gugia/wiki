package com.personal.wiki.designpattern;

import com.personal.wiki.designpattern.singleton.SimpleSingleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 单例模式例子
 *
 * @author L.X <gugia@qq.com>
 */
public class SingletonExample {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        System.out.println("运行非线程安全单例");
        for (int i = 0; i < 10; i++) {
            fixedThreadPool.execute(new SingletonTask("A" + i));
        }
        Thread.sleep(500);
        System.out.println("运行线程安全单例");
        for (int i = 0; i < 10; i++) {
            fixedThreadPool.execute(new SafeSingletonTask("B" + i));
        }
        Thread.sleep(500);
        System.out.println("运行非线程安全单例");
        for (int i = 0; i < 10; i++) {
            fixedThreadPool.execute(new SingletonTask("C" + i));
        }
        fixedThreadPool.shutdown();
        fixedThreadPool.awaitTermination(5, TimeUnit.MINUTES);
    }
}

class SingletonTask implements Runnable {

    private final String id;

    SingletonTask(String id) {
        this.id = id;
    }

    @Override
    public void run() {
        SimpleSingleton singleton = SimpleSingleton.getSingleton(id);
        System.out.println("获取单例：" + singleton.toString());
        for (int i = 0; i < 10; i++) {
            singleton.printNum();
        }
    }
}

class SafeSingletonTask implements Runnable {

    private final String id;

    SafeSingletonTask(String id) {
        this.id = id;
    }

    @Override
    public void run() {
        SimpleSingleton singleton = SimpleSingleton.getSingletonThreadSafe(id);
        System.out.println("获取单例：" + singleton.toString());
        for (int i = 0; i < 10; i++) {
            singleton.printNum();
        }
    }
}