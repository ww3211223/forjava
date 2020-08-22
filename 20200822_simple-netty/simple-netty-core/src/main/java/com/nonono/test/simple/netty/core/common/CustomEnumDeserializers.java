package com.nonono.test.simple.netty.core.common;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.nonono.test.ame.core.jackson.JsonEnumDeserializer;

public class CustomEnumDeserializers extends SimpleDeserializers {
    @Override
    @SuppressWarnings("unchecked")
    public JsonDeserializer<?> findEnumDeserializer(Class<?> type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        return createDeserializer((Class<Enum>) type);
    }

    private <T extends Enum<T>> JsonDeserializer<?> createDeserializer(Class<T> enumCls) {
        return new JsonEnumDeserializer<>(enumCls);
    }
}
