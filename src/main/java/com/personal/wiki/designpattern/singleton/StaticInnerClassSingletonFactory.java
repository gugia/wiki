package com.personal.wiki.designpattern.singleton;

/**
 * 静态内部类实现单例模式(懒汉)
 *
 * @author L.X <gugia@qq.com>
 */
public class StaticInnerClassSingletonFactory {

    private static Singleton singleton;

    private static class Singleton {
        private static int num = 2;

        public void printSquareOfNum() {
            for (int i = 0; i < 10; i++) {
                System.out.println(num * num);
            }
        }
    }

    /**
     * 懒汉模式获取单例对象，非线程安全
     *
     * @return Singleton实例
     */
    public static Singleton getSingleton() {
        if (singleton == null) {
            return new Singleton();
        } else {
            return singleton;
        }
    }

    /**
     * 懒汉模式获取单例对象，通过方法加锁实现线程安全
     *
     * @return Singleton实例
     */
    public static synchronized Singleton getSingletonThreadSafe() {
        if (singleton == null) {
            return new Singleton();
        } else {
            return singleton;
        }
    }
}
