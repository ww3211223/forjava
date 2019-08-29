package com.nonono.test.Service;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.ContinuousProcessingTimeTrigger;

public class TestService {

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

    public static void testValueState() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.fromElements(Tuple2.of(1L, 3L), Tuple2.of(1L, 5L), Tuple2.of(1L, 7L), Tuple2.of(1L, 4L), Tuple2.of(1L, 2L),
                Tuple2.of(2L, 13L), Tuple2.of(2L, 9L), Tuple2.of(2L, 27L), Tuple2.of(2L, 54L), Tuple2.of(2L, 108L))
                .keyBy(t -> t.f0)
                .flatMap(new TestValueStateFunction())
                .print();

        // execute program
        env.execute("Flink Streaming Java API Skeleton");
    }

    public static void testListState() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.fromElements(Tuple2.of(1L, 3L), Tuple2.of(1L, 5L), Tuple2.of(1L, 7L), Tuple2.of(1L, 4L), Tuple2.of(1L, 2L),
                Tuple2.of(2L, 13L), Tuple2.of(2L, 9L), Tuple2.of(2L, 27L), Tuple2.of(2L, 54L), Tuple2.of(2L, 108L))
                .keyBy(t -> t.f0)
                .flatMap(new TestListStateFunction())
                .print();

        // execute program
        env.execute("Flink Streaming Java API Skeleton");
    }

    public static void testReducingState() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.fromElements(Tuple2.of(1L, 3L), Tuple2.of(1L, 5L), Tuple2.of(1L, 7L), Tuple2.of(1L, 4L), Tuple2.of(1L, 2L))
                .keyBy(t -> t.f0)
                .reduce(new TestReducingStateFunction())
                .print();

        // execute program
        env.execute("Flink Streaming Java API Skeleton");
    }

}
