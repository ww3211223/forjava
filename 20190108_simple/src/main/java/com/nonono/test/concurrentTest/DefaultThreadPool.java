package com.nonono.test.concurrentTest;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 默认线程池实现
 *
 * @param <Job>
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    //线程池最大限制数
    private static final int MAX_WORKER_NUMBERS = 10;
    //线程池默认的数量
    private static final int DEFAULT_WORKER_NUMBERS = 5;
    //线程池最小的数量
    private static final int MIN_WORKER_NUMBERS = 1;
    //这是一个工作列表，将会向里面插入工作
    private final LinkedList<Job> jobs = new LinkedList<Job>();
    //工作者列表
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<Worker>());
    //工作者线程的数量
    private int workerNum = DEFAULT_WORKER_NUMBERS;
    //线程编号生成
    private AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool() {
        initializeWorker(DEFAULT_WORKER_NUMBERS);
    }

    public DefaultThreadPool(int num) {
        workerNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : num < MIN_WORKER_NUMBERS ? MIN_WORKER_NUMBERS : num;
        initializeWorker(workerNum);
    }

    /**
     * 执行一个Job，这个Job需要实现Runnable
     *
     * @param job
     */
    @Override
    public void execute(Job job) {
        if (job != null) {
            //添加一个工作，然后进行通知，必须使用synchronized
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    /**
     * 关闭线程
     */
    @Override
    public void shutdown() {
        for (Worker worker : workers) {
            worker.shutdown();
        }
    }

    /**
     * 增加工作者线程
     *
     * @param num
     */
    @Override
    public void addWorkers(int num) {
        synchronized (jobs) {
            //限制新增的Worker数量不能超过最大值
            if (num + this.workerNum > MAX_WORKER_NUMBERS) {
                num = MAX_WORKER_NUMBERS - this.workerNum;
            }
            initializeWorker(num);
            this.workerNum += num;
        }
    }

    /**
     * 减少工作者线程，至少保留一个线程
     *
     * @param num
     */
    @Override
    public void removeWorker(int num) {
        synchronized (jobs) {
            //为什么要大于等于workerNum
            Preconditions.checkArgument(num < this.workerNum, "beyond workNum");
            //按照给定的数量停止Worker
            int count = 0;
            while (count < num) {
                Worker worker = workers.get(count);
                if (workers.remove(worker)) {
                    worker.shutdown();
                    count++;
                }
            }
            this.workerNum -= count;
        }
    }

    /**
     * 得到正在等待执行的任务数量
     *
     * @return
     */
    @Override
    public int getJobSize() {
        return jobs.size();
    }

    /**
     * 初始化线程工作者
     *
     * @param num
     */
    private void initializeWorker(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPol-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }

    /**
     * 内部工作线程
     */
    class Worker implements Runnable {
        //是否工作
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Job job = null;
                synchronized (jobs) {
                    //如果工作者列表是空的，那么就wait
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            //感知到外部对WorkerThread的中断操作，返回
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    //取出一个Job
                    job = jobs.removeFirst();
                }
                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception ex) {
                    }
                }
            }
            System.out.println("线程：" + Thread.currentThread().getName() + "，检测到当前线程中断。");
        }

        public void shutdown() {
            running = false;
        }
    }
}
