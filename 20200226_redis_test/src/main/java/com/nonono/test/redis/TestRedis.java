package com.nonono.test.redis;

import com.alibaba.fastjson.JSON;
import com.nonono.test.redis.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
        String key = "NONONO:TEST:PROJECT";

        Product product = new Product(1, "艾查恩", LocalDateTime.now());
        ValueOperations<String, Product> operations = redisTemplate.opsForValue();
        operations.set(key, product, 10, TimeUnit.SECONDS);
        System.out.println("testRedis.testObj() hasKey:" + redisTemplate.hasKey(key));
        System.out.println(JSON.toJSON(redisTemplate.opsForValue().get(key)));
    }

    public void testRedisLock() {
        final String lockKey = "LOCK_KEY";
        final int expireTime = 3;
        AtomicInteger num = new AtomicInteger();

        for (int i = 0; i < 5; i++) {
            testThreadPool.execute(() -> {
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
            });
        }
    }

    public void testList() {
        String key = "NONONO:TEST:LISTS";
        for (int i = 0; i < 10; i++) {
            String testStr = "test_" + i;
            redisTemplate.opsForList().rightPush(key, testStr);
        }

        for (int i = 0; i < 20; i++) {
            Object result = redisTemplate.opsForList().leftPop(key);
            if (result == null) {
                break;
            }
            System.out.println("list left pop:" + result.toString());
        }
    }

    public void testSet() {
        String key = "NONONO:TEST:SETS";
        for (int i = 0; i < 20; i++) {
            String testStr = "test_" + i % 8;
            redisTemplate.opsForSet().add(key, testStr);
        }

        for (int i = 0; i < 20; i++) {
            Object result = redisTemplate.opsForSet().pop(key);
            if (result == null) {
                break;
            }
            System.out.println("set pop:" + result.toString());
        }
    }

    public void testKeys() {
        String keyPrefix = "NONONO:TEST:TEXT:";
        stringRedisTemplate.opsForValue().set(keyPrefix + "A1", "1", 30, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(keyPrefix + "A2", "12", 30, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(keyPrefix + "A3", "123", 30, TimeUnit.SECONDS);

        Set keys = redisTemplate.keys(keyPrefix + "*");
        System.out.println("keys:" + keys);
    }

    public void testScanKeys() {
        System.out.println("testScanKeys start.");

        String keyPrefix = "NONONO:TEST:SCANKEYS:";
        stringRedisTemplate.opsForValue().set(keyPrefix + "A1", "1", 30, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(keyPrefix + "A2", "12", 30, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(keyPrefix + "A3", "123", 30, TimeUnit.SECONDS);

        try {
            ScanOptions scanOptions = ScanOptions.scanOptions().match(keyPrefix + "*").count(1000).build();
            RedisSerializer<String> keySerializer = (RedisSerializer<String>) stringRedisTemplate.getKeySerializer();
            Cursor<String> cursor = (Cursor) stringRedisTemplate.executeWithStickyConnection((RedisCallback) redisConnection -> new ConvertingCursor<>(redisConnection.scan(scanOptions), keySerializer::deserialize));
            while (cursor.hasNext()) {
                Object key = cursor.next();
                System.out.println("testScan Key: " + key);
            }

            cursor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("testScanKeys end.");
    }

    public void testDelayQueue() {
        String key = "NONONO:TEST:DELAY_QUEUE:A1";
        System.out.println(LocalTime.now() + " testDelayQueue start.");
        stringRedisTemplate.opsForZSet().add(key, "V2", System.currentTimeMillis() + 3000);
        stringRedisTemplate.opsForZSet().add(key, "V3", System.currentTimeMillis() + 5000);
        stringRedisTemplate.opsForZSet().add(key, "V1", System.currentTimeMillis() + 2000);

        while (true) {
            Set<String> rangeResult = stringRedisTemplate.opsForZSet().rangeByScore(key, 0, System.currentTimeMillis(), 0, 1);
            if (rangeResult.isEmpty()) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            System.out.println(LocalTime.now() + " rangeResult:" + rangeResult.toString());
            String value = rangeResult.iterator().next();
            if (stringRedisTemplate.opsForZSet().remove(key, value) > 0) {
                System.out.println(LocalTime.now() + " remove:" + value);
            }
        }
    }
}
