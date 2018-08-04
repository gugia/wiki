package com.personal.wiki.designpattern.singleton;

/**
 * 静态内部类实现单例模式(懒汉)
 *
 * @author L.X <gugia@qq.com>
 */
public class SimpleSingleton {

    private static SimpleSingleton singleton;
    private String name;
    private Integer num = 1;

    private SimpleSingleton() {

    }

    private SimpleSingleton(String name) {
        this.name = name;
    }

    public void printNum() {
        System.out.println(name + ".num=" + num++);
    }

    /**
     * 懒汉模式获取单例对象，非线程安全
     *
     * @return Singleton实例
     */
    public static SimpleSingleton getSingleton(String name) {
        if (singleton == null) {
            singleton = new SimpleSingleton(name);
        }
        return singleton;
    }

    /**
     * 懒汉模式获取单例对象，通过方法加锁实现线程安全
     *
     * @return Singleton实例
     */
    public static synchronized SimpleSingleton getSingletonThreadSafe(String name) {
        if (singleton == null) {
            singleton = new SimpleSingleton(name);
        }
        return singleton;
    }
}
