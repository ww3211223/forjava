package com.nonono.test.ame.core.exception;

public class JsonParseRuntimeException extends RuntimeException {
    public JsonParseRuntimeException(String message) {
        super(message);
    }

    public JsonParseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
