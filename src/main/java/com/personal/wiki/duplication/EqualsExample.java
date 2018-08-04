/*
 * Copyright 2018 Pivotal Software, Inc..
 * Licensed under the Apache License 2.0.
 */
package com.personal.wiki.duplication;

import java.util.LinkedList;

/**
 * Object.equals 例子
 *
 * @author L.X <gugia@qq.com>
 */
public class EqualsExample {

    public static void main(String[] args) {
        Treenode n1 = new Treenode();
        n1.setName("Max");
        n1.setAge(0);
        Treenode n2 = new Treenode();
        n2.setName("Max");
        n2.setAge(0);
        System.out.println(n1.equals(n2));
        n1.setChildren(new LinkedList<>());
        System.out.println(n1.equals(n2));
        n2.setChildren(new LinkedList<>());
        System.out.println(n1.equals(n2));
    }
}
