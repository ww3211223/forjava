package com.nonono.test.Sort;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class QuickSort {
    private void swap(int[] A, int left, int right) {
        int temp = A[right];
        A[right] = A[left];
        A[left] = temp;
    }

    private int partition(int[] A, int left, int right) {
        int pivot = A[right];
        int tail = left - 1;
        for (int i = left; i < right; i++) {
            if (A[i] <= pivot) {
                swap(A, i, ++tail);
            }
        }
        swap(A, ++tail, right);
        return tail;
    }

    public void sort(int[] A, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(A, left, right);
        sort(A, left, pivot - 1);
        sort(A, pivot + 1, right);
    }

    public void test() {
        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            list.add(random.nextInt(100));
        }

        int[] A = list.stream().mapToInt(Integer::intValue).toArray();
        System.out.println(String.format("排序前：%s", Arrays.toString(A)));
        this.sort(A, 0, A.length - 1);
        System.out.println(String.format("排序后：%s", Arrays.toString(A)));
    }

}
