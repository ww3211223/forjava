package com.nonono.test.redis;

import com.nonono.test.redis.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TestRedis {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisLockHelper redisLockHelper;

    private ExecutorService testThreadPool;

    @PostConstruct
    public void init() {
        testThreadPool = new ThreadPoolExecutor(5, 5, 1000, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    public void testStr() {
        stringRedisTemplate.opsForValue().set("abc2", "123456", 10, TimeUnit.SECONDS);
        System.out.println("testRedis.test(): " + stringRedisTemplate.opsForValue().get("abc"));

    }

    public void testIncr() {
        stringRedisTemplate.opsForValue().increment("increment");
        System.out.println("testIncr is end.");
    }

    public void testObj() {
        Product product = new Product(1, "加达里", LocalDateTime.now());
        ValueOperations<String, Product> operations = redisTemplate.opsForValue();
        operations.set("product", product, 10, TimeUnit.SECONDS);
        System.out.println("testRedis.testObj():" + redisTemplate.hasKey("product"));
    }

    public void testRedisLock() {
        final String lockKey = "LOCK_KEY";
        final int expireTime = 3;
        AtomicInteger num = new AtomicInteger();

//        for (int i = 0; i < 5; i++) {
//            testThreadPool.execute(() -> {
        while (true) {
            try {
                String threadName = Thread.currentThread().getName();
                String value = threadName + System.nanoTime();
                boolean hasLock = redisLockHelper.getLock(lockKey, value, expireTime);
                if (hasLock) {
                    num.getAndIncrement();
                    System.out.println(threadName + "|获取到了锁." + LocalDateTime.now().toString());
                }
                if (num.get() >= 3) {
                    return;
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("线程执行异常，异常信息：" + e.getMessage());
            }
        }
//            });
//        }
    }
}
