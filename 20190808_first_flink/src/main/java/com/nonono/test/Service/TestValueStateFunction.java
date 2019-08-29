package com.nonono.test.Service;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;
import scala.Console;

public class TestValueStateFunction extends RichFlatMapFunction<Tuple2<Long, Long>, Tuple2<Long, Long>> {

    private transient ValueState<Tuple2<Long, Long>> sum;

    @Override
    public void flatMap(Tuple2<Long, Long> input, Collector<Tuple2<Long, Long>> out) throws Exception {
        Tuple2<Long, Long> currentSum = sum.value();
        if (currentSum == null) {
            currentSum = Tuple2.of(0L, 0L);
        }

        currentSum.f0 = input.f0;
        currentSum.f1 += input.f1;
        sum.update(currentSum);

        Console.println("input:" + input);
        Console.println("currentSum:" + currentSum);
        Console.println("----------------------------");

//        if (currentSum.f0 >= 2) {
//            out.collect(new Tuple2<>(input.f0, currentSum.f1 / currentSum.f0));
//            sum.clear();
//        }
    }

    @Override
    public void open(Configuration config) {
        ValueStateDescriptor<Tuple2<Long, Long>> descriptor =
                new ValueStateDescriptor<Tuple2<Long, Long>>(
                        "average",
                        TypeInformation.of(new TypeHint<Tuple2<Long, Long>>() {
                        }));
        sum = getRuntimeContext().getState(descriptor);
    }
}
