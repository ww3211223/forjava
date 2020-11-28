package com.nonono.test.redis;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestRedissonLock {

    private RedissonClient redissonClient;

    public TestRedissonLock(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    private ExecutorService testThreadPool;

    @PostConstruct
    public void init() {
        testThreadPool = new ThreadPoolExecutor(5, 5, 1000, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    /**
     * 测试可重入锁
     * 线程休眠2秒
     *
     * @param leaseTime 最大持有锁时长（秒）
     */
    public void testReentrantLock(long leaseTime) {
        testThreadPool.execute(() -> {
            String key = "redisson:test:key";
            System.out.println(Thread.currentThread().getName() + " | testReentrantLock begin." + LocalDateTime.now().toString());
            RLock lock = redissonClient.getLock(key);

            lock.lock(leaseTime, TimeUnit.MILLISECONDS);
            System.out.println(Thread.currentThread().getName() + " | testReentrantLock lock." + LocalDateTime.now().toString());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " | testReentrantLock end." + LocalDateTime.now().toString());
            lock.unlock();
        });
    }

    public void testReentrantLock2() {
        testThreadPool.execute(() -> {
            String key = "redisson:test:key";
            System.out.println(Thread.currentThread().getName() + " | testReentrantLock2 begin." + LocalDateTime.now().toString());
            RLock lock = redissonClient.getLock(key);
            try {
                if (lock.tryLock(300, TimeUnit.MILLISECONDS)) {
                    System.out.println(Thread.currentThread().getName() + " | testReentrantLock2 尝试获取锁成功." + LocalDateTime.now().toString());
                } else {
                    System.out.println(Thread.currentThread().getName() + " | testReentrantLock2 尝试获取锁失败." + LocalDateTime.now().toString());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
