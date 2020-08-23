package com.nonono.test.simple.netty.core.common;

import java.util.concurrent.atomic.AtomicLong;

public class RequestSequence {

    private static final AtomicLong seq = new AtomicLong(11200000);

    /**
     * next
     * @return nex
     */
    public static long next() {
        return System.currentTimeMillis() + seq.incrementAndGet();
    }
}
