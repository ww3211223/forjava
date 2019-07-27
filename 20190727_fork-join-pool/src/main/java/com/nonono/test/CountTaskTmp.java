package com.nonono.test;

import java.util.concurrent.RecursiveTask;

/**
 * 测试ForkJoinPool
 */
public class CountTaskTmp extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 2;

    private int start;

    private int end;

    public CountTaskTmp(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        System.out.println(String.format("ThreadName：%s，compute()被调用.", Thread.currentThread().getName()));
        int sum = 0;
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            //如果任务大于阈值，就分裂成两个子任务计算
            int mid = (start + end) / 2;
            CountTaskTmp leftTask = new CountTaskTmp(start, mid);
            CountTaskTmp rightTask = new CountTaskTmp(mid + 1, end);

            leftTask.fork();
            rightTask.fork();

            int leftResult = leftTask.join();
            int rightResult = rightTask.join();

            sum = leftResult + rightResult;
        }

        return sum;
    }
}
