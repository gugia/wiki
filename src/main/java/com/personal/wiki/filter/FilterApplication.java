package com.personal.wiki.filter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * FilterApplication
 *
 * @author L.X <gugia@qq.com>
 */
public class FilterApplication {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new FilterTask());
    }
}
