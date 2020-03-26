package guavaTest;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 测试guavaCache
 */
public class TestGuavaCache {

    private Cache<Integer, Object> cache;

    public TestGuavaCache() {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(10000, TimeUnit.MILLISECONDS)
                .initialCapacity(16)
                .maximumSize(1000)
                .concurrencyLevel(8)
                .build();
    }

    public void test() {
        TestModel test1 = new TestModel(1, "加达里");
        TestModel test2 = new TestModel(2, "米玛塔尔");

        setCache(test1.getId(), test1);
        setCache(test2.getId(), test2);
        System.out.println(JSON.toJSON(getCache(test2.getId())));
    }

    private TestModel getCache(Integer id) {
        return (TestModel) cache.getIfPresent(id);
    }

    private void setCache(Integer id, TestModel model) {
        cache.put(id, model);
    }

}
