package com.personal.wiki.filter;

import java.util.LinkedList;
import java.util.List;

/**
 * FilterChain
 *
 * @author L.X <gugia@qq.com>
 */
public class FilterChain {

    private final List<IFilter> filters = new LinkedList<>();
    private int pos = 0;

    public void addFilter(IFilter filter) {
        this.filters.add(filter);
    }

    public void doHandle() {
        if (pos < filters.size()) {
            IFilter filter = filters.get(pos++);
            filter.handle(this);
        } else {
            pos = 0;
        }
    }
}
