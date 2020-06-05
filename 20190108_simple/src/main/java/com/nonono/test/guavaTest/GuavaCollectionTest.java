package com.nonono.test.guavaTest;

import com.google.common.collect.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * guava集合测试
 */
public class GuavaCollectionTest {

    public void test() {
        testImmutability();
        testMulti();
        testTable();
        testRangeSet();
        testSets();
        testRangeSet2();
    }

    /**
     * 测试不可变集合
     */
    private void testImmutability() {
        System.out.println("----testImmutability()----");
        ImmutableSet<Integer> numbers = ImmutableSet.of(1, 2, 3, 4, 5, 6, 8);
        System.out.println("ImmutableSet: " + numbers);
        ImmutableList<String> lists = ImmutableList.of("加达里", "艾玛", "盖伦特", "米玛塔尔");
        System.out.println("ImmutableList: " + lists);
    }

    private void testMulti() {
        System.out.println("----testMulti()----");
        Multimap<Integer, String> multimap = HashMultimap.create();
        multimap.put(1, "加达里");
        multimap.put(1, "米玛塔尔");
        multimap.put(2, "艾玛");

        System.out.println("multimap:" + multimap);
        System.out.println("multimap.get(1): " + multimap.get(1));

        Multiset<Integer> multiset = HashMultiset.create();
        multiset.add(10);
        multiset.add(20);
        multiset.add(30);
        multiset.add(30);
        System.out.println("multiset: " + multiset);
        System.out.println("multiset.count(30): " + multiset.count(30));
        System.out.println("multiset.size(): " + multiset.size());

        ImmutableSet<String> digits = ImmutableSet.of("aa", "bb", "ccc", "dddd", "ffff");
        Multimap<Integer, String> multimap2 = Multimaps.index(digits, String::length);

        System.out.println("multimap2：" + multimap2);
    }

    private void testTable() {
        System.out.println("----testTable()----");
        Table<Integer, String, String> table = HashBasedTable.create();
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 4; j++) {
                table.put(i, "row_" + i + "_column_" + j, "value_" + j);
            }
        }

        System.out.println("table.row(5): " + table.row(5));
        System.out.println("table.get(5,'row_5_column_2'): " + table.get(5, "row_5_column_2"));
    }

    private void testRangeSet() {
        System.out.println("----testRangeSet()----");
        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10));
        rangeSet.add(Range.closed(25, 30));
        System.out.println("rangeSet: " + rangeSet);

        rangeSet.add(Range.closedOpen(5, 15));
        System.out.println("rangeSet: " + rangeSet);

//        rangeSet.add(Range.atLeast(40));
//        System.out.println("rangeSet: " + rangeSet);

        rangeSet.remove(Range.openClosed(10, 20));
        System.out.println("rangeSet: " + rangeSet);

        System.out.println("rangeSet.contains(10): " + rangeSet.contains(10));
        System.out.println("rangeSet.contains(15): " + rangeSet.contains(15));

        System.out.println("rangeSet.rangeContaining(10): " + rangeSet.rangeContaining(10));
        System.out.println("rangeSet.encloses1: " + rangeSet.encloses(Range.openClosed(3, 10)));
        System.out.println("rangeSet.encloses2: " + rangeSet.encloses(Range.openClosed(5, 12)));
        System.out.println("rangeSet.span(): " + rangeSet.span());
    }

    private void testRangeSet2() {
        System.out.println("----testRangeSet2()----");
        RangeSet<LocalDateTime> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(LocalDateTime.of(2020, 06, 03, 13, 50, 20), LocalDateTime.now()));
        System.out.println("rangeSet.contains1:" + rangeSet.contains(LocalDateTime.of(2020, 06, 05, 13, 47, 20)));
        System.out.println("rangeSet.contains2:" + rangeSet.contains(LocalDateTime.of(2020, 06, 03, 18, 47, 20)));
    }

    /**
     * 测试Set交集、并集、差集
     */
    private void testSets() {
        Set<Integer> set1 = Sets.newHashSet(1, 2, 3, 5, 8);
        Set<Integer> set2 = Sets.newHashSet(2, 4, 6, 8, 10);

        //并集
        System.out.println("并集：" + Sets.union(set1, set2));
        //差集
        System.out.println("差集：" + Sets.difference(set1, set2));
        //交集
        System.out.println("交集：" + Sets.intersection(set1, set2));
    }
}
