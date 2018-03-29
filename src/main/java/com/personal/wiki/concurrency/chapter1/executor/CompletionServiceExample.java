package com.personal.wiki.concurrency.chapter1.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 多线程 - 得到异步执行结果例子2
 *
 * @author L.X <gugia@qq.com>
 */
public class CompletionServiceExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newCachedThreadPool();
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(service);
        for (int i = 0; i < 5; i++) {
            completionService.submit(new TaskInteger(i));
        }
        service.shutdown();
        /* 此处会阻塞 */
        for (int i = 0; i < 5; i++) {
            Future<Integer> future = completionService.take();
            System.out.println(future.get());
        }
    }
}

class TaskInteger implements Callable<Integer> {

    private final int sum;

    TaskInteger(int sum) {
        this.sum = sum;
    }

    @Override
    public Integer call() throws Exception {
        TimeUnit.SECONDS.sleep(sum);
        return sum * sum;
    }
}
