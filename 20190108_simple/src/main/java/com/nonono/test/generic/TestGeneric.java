package com.nonono.test.generic;

public class TestGeneric<T> {
    private T context;

    public void setContext(T context) {
        this.context = context;
    }

    public T getContext() {
        return context;
    }
}
