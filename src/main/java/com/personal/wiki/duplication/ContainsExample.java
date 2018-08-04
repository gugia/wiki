/*
 * Copyright 2018 Pivotal Software, Inc..
 * Licensed under the Apache License 2.0.
 */
package com.personal.wiki.duplication;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * List.contains 例子
 *
 * @author L.X <gugia@qq.com>
 */
public class ContainsExample {

    public static void main(String[] args) {
        List<Treenode> list = new LinkedList<>();
        Treenode n1 = new Treenode();
        n1.setName("Max");
        n1.setAge(0);
        list.add(n1);
        Treenode n2 = new Treenode();
        n2.setName("Max");
        n2.setAge(0);
        System.out.println(list.contains(n2));
        n1.setChildren(new LinkedList<>());
        System.out.println(n1.equals(n2));
        n2.setChildren(new LinkedList<>());
        System.out.println(n1.equals(n2));
        Map<String[], Treenode> map = new LinkedHashMap<>();
        String a = "1", b = "2", c = "3", d = "4";
        String[] arr1 = {a, b, c};
        String[] arr2 = {a, b, d};
        map.put(arr1, n1);
        System.out.println(map.get(arr1).equals(n2));
        n1.getChildren().add(n2);
        Map<String[], Treenode> mapse = new LinkedHashMap<>();
        mapse.put(arr2, n2);
        Treenode n3 = new Treenode();
        n3.setName("Luo");
        n3.setAge(0);
        n2.getChildren().add(n3);
        System.out.println(map.get(arr1).getChildren().get(0).getChildren().get(0).equals(n3));
    }
}
