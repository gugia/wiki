package com.personal.wiki.filter;

/**
 * FilterOne
 *
 * @author L.X <gugia@qq.com>
 */
public class FilterOne implements IFilter {

    @Override
    public void handle(FilterChain chain) {
        System.out.println("Filter One handled!");
        chain.doHandle();
    }
}
