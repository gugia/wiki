package com.personal.wiki.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 排序 - 冒泡
 *
 * @author L.X <gugia@qq.com>
 */
public class Bubble {

    static Random rand = new Random();
    static final int NUM = 50000;
    static int[] array1 = new int[NUM];
    static int[] array2 = new int[NUM];
    static int[] array3 = new int[NUM];
    static int[] array4 = new int[NUM];

    public static void main(String[] args) {
        /* 初始化 */
        for (int i = 0; i < NUM; i++) {
            array1[i] = rand.nextInt(9999);
        }
        array2 = Arrays.copyOf(array1, NUM);
        array3 = Arrays.copyOf(array1, NUM);
        array4 = Arrays.copyOf(array1, NUM);
        /* 一般排序 */
        long start = System.currentTimeMillis();
        bubbleSort(array1);
        long end = System.currentTimeMillis();
        System.out.println("Time span: " + (end - start));
        /* 更快排序 */
        start = System.currentTimeMillis();
        FasterBubbleSort(array2);
        end = System.currentTimeMillis();
        System.out.println("Time span: " + (end - start));
        /* 最快排序 */
        start = System.currentTimeMillis();
        FastestBubbleSort(array3);
        end = System.currentTimeMillis();
        System.out.println("Time span: " + (end - start));
        System.out.println(Arrays.toString(array3));
        /* 最快排序2 */
        start = System.currentTimeMillis();
        FastestBubbleSortReverse(array4);
        end = System.currentTimeMillis();
        System.out.println("Time span: " + (end - start));
        System.out.println(Arrays.toString(array4));
    }

    public static void bubbleSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = a.length - 1; j > i - 1; j--) {
                if (a[j] < a[j - 1]) {
                    int temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

    public static void FasterBubbleSort(int[] a) {
        boolean flag = false;
        for (int i = 1; i < a.length; i++) {
            for (int j = a.length - 1; j > i - 1; j--) {
                if (a[j] < a[j - 1]) {
                    int temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
    }

    public static void FastestBubbleSort(int[] a) {
        int flag = 0, last;
        while (flag < a.length - 1) {
            last = flag;
            flag = a.length - 1;
            for (int j = a.length - 1; j > last; j--) {
                if (a[j] < a[j - 1]) {
                    int temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                    flag = j - 1;
                }
            }
        }
    }

    public static void FastestBubbleSortReverse(int[] a) {
        int j, k;
        int flag = a.length;//flag来记录最后交换的位置，也就是排序的尾边界
        while (flag > 0) {//排序未结束标志
            k = flag; //k 来记录遍历的尾边界
            flag = 0;
            for (j = 1; j < k; j++) {
                if (a[j - 1] > a[j]) {//前面的数字大于后面的数字就交换
                    //交换a[j-1]和a[j]
                    int temp;
                    temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                    //表示交换过数据;
                    flag = j;//记录最新的尾边界.
                }
            }
        }
    }
}
