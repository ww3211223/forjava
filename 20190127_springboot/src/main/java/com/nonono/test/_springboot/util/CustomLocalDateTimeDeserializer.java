package com.nonono.test._springboot.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 定制LocalDateTime反序列化器
 */
public class CustomLocalDateTimeDeserializer extends LocalDateTimeDeserializer {

    public CustomLocalDateTimeDeserializer(DateTimeFormatter formatter) {
        super(formatter);
    }

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String text = parser.getText();
        if (StringUtils.isEmpty(text)) {
            return null;
        }

        return LocalDateTimeHelper.parse(text);
    }
}
