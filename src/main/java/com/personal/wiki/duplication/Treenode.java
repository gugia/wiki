/*
 * Copyright 2018 Pivotal Software, Inc..
 * Licensed under the Apache License 2.0.
 */
package com.personal.wiki.duplication;

import java.util.List;
import java.util.Objects;

/**
 * POJO对象
 *
 * @author L.X <gugia@qq.com>
 */
public class Treenode {

    private String name;
    private int age;
    private List<Treenode> children;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the children
     */
    public List<Treenode> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<Treenode> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Treenode) {
            return this.hashCode() == obj.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + this.age;
        hash = 17 * hash + Objects.hashCode(this.children);
        return hash;
    }
}
