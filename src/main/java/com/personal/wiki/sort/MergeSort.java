package com.personal.wiki.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import org.springframework.util.CollectionUtils;

/**
 * 排序 - 归并排序
 *
 * @author L.X <gugia@qq.com>
 */
public class MergeSort {

    static Random rand = new Random();
    static final int NUM = 10;
    static int[] array1 = new int[NUM];

    public static void main(String[] args) {
        /* 初始化 */
        for (int i = 0; i < NUM; i++) {
            array1[i] = rand.nextInt(9999);
        }
        List<Integer> a = CollectionUtils.arrayToList(array1);
        Integer[] array = (Integer[]) a.toArray();
        /* 排序 */
        long start = System.currentTimeMillis();
        sort(array1, 0, array1.length - 1);
        long end = System.currentTimeMillis();
        System.out.println("Time span: " + (end - start));
        System.out.println("排序后：\r\n" + Arrays.toString(array1));
        start = System.currentTimeMillis();
        Arrays.sort(array, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        end = System.currentTimeMillis();
        System.out.println("Time span: " + (end - start));
        System.out.println("排序后：\r\n" + Arrays.toString(array));
    }

    public static int[] sort(int[] a, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            System.out.println("before low-mid：" + low + "-" + mid);
            sort(a, low, mid);
            System.out.println("after low-mid：" + low + "-" + mid);
            System.out.println("before mid-high：" + mid + "-" + high);
            sort(a, mid + 1, high);
            System.out.println("after mid-high：" + mid + "-" + high);
            //左右归并
            System.out.println("before merge low-mid-high：" + low + "-" + mid + "-" + high);
            merge(a, low, mid, high);
            System.out.println("after merge low-mid-high：" + low + "-" + mid + "-" + high);
        }
        return a;
    }

    public static void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;
        int j = mid + 1;
        int k = 0;
        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (a[i] < a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        // 把左边剩余的数移入数组 
        while (i <= mid) {
            temp[k++] = a[i++];
        }
        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = a[j++];
        }
        // 把新数组中的数覆盖nums数组
        for (int x = 0; x < temp.length; x++) {
            a[x + low] = temp[x];
        }
    }
}
