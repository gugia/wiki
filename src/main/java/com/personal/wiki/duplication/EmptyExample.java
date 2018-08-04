/*
 * Copyright 2018 Pivotal Software, Inc..
 * Licensed under the Apache License 2.0.
 */
package com.personal.wiki.duplication;

import java.util.Arrays;
import java.util.UUID;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * StringUtils 用法
 *
 * @author L.X <gugia@qq.com>
 */
public class EmptyExample {

    public static void main(String[] args) {
        String[] a = {};
        String[] b = null;
        String[] c = {""};
        String[] d = {"d"};
        System.out.println("StringUtils.isEmpty()");
        System.out.println("a的判断结果：" + StringUtils.isEmpty(a));
        System.out.println("b的判断结果：" + StringUtils.isEmpty(b));
        System.out.println("c的判断结果：" + StringUtils.isEmpty(c));
        System.out.println("d的判断结果：" + StringUtils.isEmpty(d));
        System.out.println("CollectionUtils.isEmpty()");
        System.out.println("a的判断结果：" + CollectionUtils.isEmpty(Arrays.asList(a)));
        //System.out.println("b的判断结果：" + CollectionUtils.isEmpty(Arrays.asList(b)));
        System.out.println("c的判断结果：" + CollectionUtils.isEmpty(Arrays.asList(c)));
        System.out.println("d的判断结果：" + CollectionUtils.isEmpty(Arrays.asList(d)));
        System.out.print(UUID.randomUUID());
    }
}
