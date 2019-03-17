package com.nonono.test.Sort;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ShellSort {

    public void sort(int[] A, int n) {
        int fengeNum = 0;
        while (fengeNum <= n) {
            fengeNum = 3 * fengeNum + 1;
        }
        while (fengeNum >= 1) {
            System.out.println("----------------");
            System.out.println(String.format("第一层while循环 fengeNum:%s", fengeNum));

            for (int i = fengeNum; i < n; i++) {
                int duichengNum = i - fengeNum;//偏移量
                int currentValue = A[i];
                System.out.println(String.format("第一层for循环 i=%s duichengNum=%s currentValue=%s A[duichengNum]=%s", i, duichengNum, currentValue, A[duichengNum]));
                while (duichengNum >= 0 && A[duichengNum] > currentValue) {
                    A[duichengNum + fengeNum] = A[duichengNum];
                    duichengNum = duichengNum - fengeNum;
                    System.out.println(String.format("  第二层循环数组对象：%s", Arrays.toString(A)));
                }

                A[duichengNum + fengeNum] = currentValue;
                //System.out.println(String.format("第一层for循环end duichengNum + fengeNum=%s A[duichengNum + fengeNum]=%s", duichengNum + fengeNum, A[duichengNum + fengeNum]));
                System.out.println(String.format("数组对象：%s", Arrays.toString(A)));
                System.out.println("----------------");
            }
            fengeNum = (fengeNum - 1) / 3;
        }
    }

    public void test() {
        int[] A = {4, 7, 6, 1, 13, 74, 56, 8, 5, 9, 54, 71, 14};
        System.out.println(String.format("shellSort执行开始，%s", Arrays.toString(A)));
        this.sort(A, A.length);
        System.out.println(String.format("shellSort执行结束，%s", Arrays.toString(A)));
    }

}
