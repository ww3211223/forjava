package com.nonono.test.Service;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * 分割器
 */
public class Spitter implements FlatMapFunction<String, Tuple2<String, Integer>> {

    @Override
    public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
        for (String word : s.split(" ")) {
            collector.collect(new Tuple2<String, Integer>(word, 1));
        }
    }
}
