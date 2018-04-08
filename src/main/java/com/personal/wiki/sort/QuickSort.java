package com.personal.wiki.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 排序 - 快速排序
 *
 * @author L.X <gugia@qq.com>
 */
public class QuickSort {

    static Random rand = new Random();
    static final int NUM = 50000;
    static int[] array1 = new int[NUM];
    static int[] array2 = new int[NUM];

    public static void main(String[] args) {
        /* 初始化 */
        for (int i = 0; i < NUM; i++) {
            array1[i] = rand.nextInt(9999);
        }
        array2 = Arrays.copyOf(array1, NUM);
        System.out.println("初始化：\r\n" + Arrays.toString(array1));
        /* 排序 */
        long start = System.currentTimeMillis();
        quickSort(array1, 0, NUM - 1);
        long end = System.currentTimeMillis();
        System.out.println("Time span: " + (end - start));
        System.out.println("排序后：\r\n" + Arrays.toString(array1));
        start = System.currentTimeMillis();
        Arrays.sort(array2);
        end = System.currentTimeMillis();
        System.out.println("Time span: " + (end - start));
    }

    public static void quickSort(int[] a, int low, int high) {
        if (low > high) {
            return;
        }
        int i = low;
        int j = high;
        int key = a[low];
        while (i < j) {
            while (i < j && a[j] > key) {
                j--;
            }
            while (i < j && a[i] <= key) {
                i++;
            }
            if (i < j) {
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
        int p = a[i];
        a[i] = a[low];
        a[low] = p;
        //System.out.println(Arrays.toString(a));
        quickSort(a, low, i - 1);
        quickSort(a, i + 1, high);
    }
}
