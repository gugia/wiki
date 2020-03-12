package com.personal.wiki.filter;

import java.util.concurrent.TimeUnit;

/**
 * FilterTask
 *
 * @author L.X <gugia@qq.com>
 */
public class FilterTask implements Runnable {

    private volatile boolean stop = false;
    private final FilterChain filterChain;

    public FilterTask() {
        this.filterChain = new FilterChain();
        filterChain.addFilter(new FilterOne());
        filterChain.addFilter(new FilterTwo());
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            filterChain.doHandle();
        }
    }
}
