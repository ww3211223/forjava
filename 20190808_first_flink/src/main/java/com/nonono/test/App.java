package com.nonono.test;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple2<String, Integer>> dataStream = null;
        dataStream = env
                .socketTextStream("localhost", 9999)
                .flatMap((String s, Collector<Tuple2<String, Integer>> collector) -> {
                    for (String word : s.split(" ")) {
                        collector.collect(new Tuple2<String, Integer>(word, 1));
                    }
                })
                .keyBy(0)
                .timeWindow(Time.seconds(5))
                .sum(1);

        dataStream.print();

        // execute program
        env.execute("Flink Streaming Java API Skeleton");
    }
}
