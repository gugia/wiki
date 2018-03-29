package com.personal.wiki.concurrency.chapter1.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 多线程 - 得到异步执行结果例子1
 *
 * @author L.X <gugia@qq.com>
 */
public class CallableExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Callable is running");
                TimeUnit.SECONDS.sleep(2);
                return 47;
            }
        });
        service.shutdown();
        System.out.println("future.get = " + future.get().toString());
    }
}
