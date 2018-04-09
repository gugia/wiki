package com.personal.wiki.recursion;

/**
 * 递归 - 递归函数的执行顺序<br>
 * 位于递归调用前的语句，和各级被调用函数具有相同的执行顺序<br>
 * 位于递归调用后的语句，执行书序和各个被调用函数的顺序相反
 *
 * @author L.X <gugia@qq.com>
 */
public class Order {

    public static void main(String[] args) {
        test(13);
    }

    public static void test(int a) {
        if (a == 0) {
            System.out.println("Done");
        } else {
            System.out.println("before>" + a % 2);
            test(a / 2);
            System.out.println("after>" + a % 2);
        }
    }
}
