package com.personal.wiki.filter;

/**
 * FilterTwo
 *
 * @author L.X <gugia@qq.com>
 */
public class FilterTwo implements IFilter {

    @Override
    public void handle(FilterChain chain) {
        System.out.println("Filter Two handled!");
        chain.doHandle();
    }
}
