package com.nonono.test.Service;

import org.apache.flink.api.common.functions.RichReduceFunction;
import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import scala.Console;

public class TestReducingStateFunction extends RichReduceFunction<Tuple2<Long, Long>> {
    private transient ReducingState<Tuple2<Long, Long>> sum;

    @Override
    public void open(Configuration config) {
        ReducingStateDescriptor<Tuple2<Long, Long>> descriptor =
                new ReducingStateDescriptor<Tuple2<Long, Long>>(
                        "testState",
//                        new ReduceFunction<Tuple2<Long, Long>>() {
//                            @Override
//                            public Tuple2<Long, Long> reduce(Tuple2<Long, Long> value1, Tuple2<Long, Long> value2) throws Exception {
//                                return Tuple2.of(value1.f0, value1.f1 + value2.f1);
//                            }
//                        },
                        (a, b) -> {
                            return Tuple2.of(a.f0, a.f1 + b.f1);
                        },
                        TypeInformation.of(new TypeHint<Tuple2<Long, Long>>() {
                        }));
        sum = getRuntimeContext().getReducingState(descriptor);
    }

    @Override
    public Tuple2<Long, Long> reduce(Tuple2<Long, Long> value1, Tuple2<Long, Long> value2) throws Exception {
        sum.add(value1);

        Console.println("value1:" + value1);
        Console.println("value2:" + value2);
        Console.println("sum.get():" + sum.get());
        Console.println("----------------------------");

        return Tuple2.of(value1.f0, (value1.f1 + value2.f1) / 2);
    }
}
