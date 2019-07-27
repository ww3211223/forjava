package com.nonono.test;

import java.util.concurrent.ForkJoinPool;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("开始测试ForkJoinPool.");
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTaskTmp countTaskTmp = new CountTaskTmp(1, 2000);
        Integer r = forkJoinPool.invoke(countTaskTmp);
        System.out.println(String.format("测试结果：%s", r));
        System.out.println("结束测试.");
    }
}
