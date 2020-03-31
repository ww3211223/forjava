package com.nonono.test.guavaTest;


import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.hash.Hashing;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * guava小工具测试
 */
public class GuavaUtilTest {

    public void test() {
        testOrdering();
        testJoinerAndSplitter();
        testConsistentHash();
        argumentCheck();
    }

    private void testOrdering() {
        System.out.println("----testOrdering()----");
        List<Integer> list = Arrays.asList(1, 5, 3, 8, 2);
        Collections.sort(list, Ordering.natural());
        System.out.println("自然顺序：" + list);

        List<String> listString = Lists.newArrayList("a", "test", "fff", "bbc");
        Collections.sort(listString);
        System.out.println("普通排序：" + listString);
        listString.sort(Ordering.usingToString());
        System.out.println("字典排序：" + listString);

        List<Integer> listNull = Lists.newArrayList(1, 7, 8, null, 4, 5);
        Collections.sort(listNull, Ordering.natural().nullsFirst());
        System.out.println("将null排在最前面：" + listNull);

        List<Integer> listMax = Lists.newArrayList(1, 7, 8, 4, 5);
        System.out.println("最大值：" + Ordering.natural().max(listMax).toString());
    }

    private void testJoinerAndSplitter() {
        System.out.println("----testJoinerAndSplitter()----");
        //字符合并
        Joiner joiner = Joiner.on(",").skipNulls();
        System.out.println("字符合并结果：" + joiner.join("加达里", "盖伦特", null, "米玛塔尔"));

        //集合合并
        List<String> list = Lists.newArrayList("加达里", "盖伦特", "米玛塔尔", "艾玛");
        System.out.println("集合合并结果：" + joiner.join(list));

        //Map合并
        Map<String, Integer> map = Maps.newHashMap();
        map.put("加达里", 1);
        map.put("盖伦特", 2);
        System.out.println("Map合并结果：" + Joiner.on(",").withKeyValueSeparator("=").join(map));

        //集合切分
        String splitStr = " ,加达里, 盖伦特,米玛塔尔";
        System.out.println("字符切分结果：" + Splitter.on(',').splitToList(splitStr));
        System.out.println("字符切分结果（忽略空字符串，去除前后空格）：" + Splitter.on(',').trimResults().omitEmptyStrings().splitToList(splitStr));

        //Map切分
        String splitMapStr = "加达里=1,艾玛=3";
        Map<String, String> split = Splitter.on(",").withKeyValueSeparator("=").split(splitMapStr);
        System.out.println("Map合并结果：" + split);

        //多字符切割
        String testStr = "a,b|c,$d";
        List<String> testResult = Splitter.onPattern("[,|$]").omitEmptyStrings().splitToList(testStr);
        System.out.println("多字符切割：" + testResult);

    }

    private void testConsistentHash() {
        System.out.println("----testConsistentHash()----");
        List<Integer> list = Lists.newArrayList(1223, 432, 1235, 321, 321, 2, 66);

        list.forEach((item) -> {
            int bucket = Hashing.consistentHash(item, 5);
            System.out.println("bucketCount:5 item: " + item + ",bucket: " + bucket);
        });

        System.out.println("--切换bucketCount--");
        list.forEach((item) -> {
            int bucket = Hashing.consistentHash(item, 4);
            System.out.println("bucketCount:4 item: " + item + ",bucket: " + bucket);
        });
        System.out.println("--切换bucketCount--");
        list.forEach((item) -> {
            int bucket = Hashing.consistentHash(item, 6);
            System.out.println("bucketCount:6 item: " + item + ",bucket: " + bucket);
        });
    }

    /**
     * 参数检查
     */
    private void argumentCheck() {
        int num = 10;
        Preconditions.checkArgument(num > 0, "num#%s must be > 0", num);
        num = -10;
        Preconditions.checkArgument(num > 0, "num#%s must be > 0", num);
    }
}
