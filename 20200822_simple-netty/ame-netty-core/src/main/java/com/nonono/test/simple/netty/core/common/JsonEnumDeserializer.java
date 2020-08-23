package com.nonono.test.simple.netty.core.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nonono.test.ame.core.enums.EnumHelper;

import java.io.IOException;

public class JsonEnumDeserializer<T extends Enum> extends JsonDeserializer<T> {

    private Class<T> enumType;

    public JsonEnumDeserializer(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String source = p.getText();
        return EnumHelper.textToEnum(source, enumType);
    }

}
