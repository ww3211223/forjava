package com.nonono.test;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.ContinuousProcessingTimeTrigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        App.testProcessWindowFunction();
    }

    public static class Spitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
            for (String word : s.split(" ")) {
                collector.collect(new Tuple2<String, Integer>(word, 1));
            }
        }
    }

    public static class MyProcessWindowFunction extends ProcessWindowFunction<Tuple2<String, Integer>, String, String, TimeWindow> {

        @Override
        public void process(String s, Context context, Iterable<Tuple2<String, Integer>> elements, Collector<String> out) throws Exception {
//            long count = 0;
//            for (Tuple2<String, Integer> in : elements) {
//                count++;
//            }

            out.collect("Window: " + context.window() + "elements: " + elements);
        }
    }

    public static void testSimple() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple2<String, Integer>> dataStream = null;
        dataStream = env
                .socketTextStream("localhost", 9999)
                .flatMap(new Spitter())
                .keyBy(0)
                .timeWindow(Time.seconds(60))
                .trigger(ContinuousProcessingTimeTrigger.of(Time.seconds(5)))
                .sum(1);

        dataStream.print();

        // execute program
        env.execute("Flink Streaming Java API Skeleton");
    }

    public static void testProcessWindowFunction() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        SingleOutputStreamOperator<String> process = env
                .socketTextStream("localhost", 9999)
                .flatMap(new Spitter())
                .keyBy(t -> t.f0)
                .timeWindow(Time.seconds(60))
                .trigger(ContinuousProcessingTimeTrigger.of(Time.seconds(5)))
                .process(new MyProcessWindowFunction());

        process.print();

        // execute program
        env.execute("Flink Streaming Java API Skeleton");
    }
}
