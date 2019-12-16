package com.nonono.test._springboot.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.ArrayUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Jackson工具类
 */
public class JacksonUtils {

    private static JacksonUtils generic = new JacksonUtils();
    private ObjectMapper mapper;

    private JacksonUtils() {
        ObjectMapper mapper = new ObjectMapper();
        //未知的属性反序列化异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        //空bean序列化失败
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //date类型转换为timestamps
        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        //显式重命名
        mapper.configure(MapperFeature.ALLOW_EXPLICIT_PROPERTY_RENAMING, true);
        //忽略大小写
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        //自动删除getter-setter字段
        mapper.configure(MapperFeature.AUTO_DETECT_FIELDS, false);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        mapper.registerModule(javaTimeModule);

        this.mapper = mapper;
    }

    public static JacksonUtils generic() {
        return generic;
    }

    public String toJson(Object obj) {
        if (obj == null) {
            return "";
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public <T> T toObj(String jsonText, Class<T> classz) {
        if (StringUtils.isEmpty(jsonText)) {
            return null;
        }
        try {
            return mapper.readValue(jsonText, classz);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 转换为泛型类型
     *
     * @param jsonText
     * @param classType
     * @param classz
     * @param <T>
     * @return
     */
    public <T> T toGenericObj(String jsonText, Class<T> classType, Class... classz) {
        if (StringUtils.isEmpty(jsonText)) {
            return null;
        }
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(classType, classz);
            return mapper.readValue(jsonText, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 转换为嵌套泛型类型
     *
     * @param jsonText
     * @param classType
     * @param classz
     * @param <T>
     * @return
     */
    public <T> T toNestRefObj(String jsonText, Class<T> classType, Class... classz) {
        if (StringUtils.isEmpty(jsonText)) {
            return null;
        }
        if (String.class.isAssignableFrom(classType)) {
            return (T) jsonText;
        }

        ParameterizedType type = null;
        try {
            if (ArrayUtils.isEmpty(classz)) {
                return toObj(jsonText, classType);
            }
            //无嵌套类型，转换为简单泛型
            if (classz.length == 1) {
                return toGenericObj(jsonText, classType, classz[0]);
            }

            //嵌套类型，使用参数化类型处理
            for (int i = classz.length - 1; i >= 0; i--) {
                if (type == null) {
                    type = CustomParameterizedType.create(classz[i], classz[--i]);
                } else {
                    type = CustomParameterizedType.create(type, classz[i]);
                }
            }

            type = CustomParameterizedType.create(type, classType);
            return mapper.readValue(jsonText, CustomTypeReference.builder().type(type).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List json2List(String jsonText) {
        if (StringUtils.isEmpty(jsonText)) {
            return null;
        }

        try {
            return mapper.readValue(jsonText, List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> List<T> json2List2(String jsonText, Class... classz) {
        if (StringUtils.isEmpty(jsonText)) {
            return null;
        }

        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, classz);
            return mapper.readValue(jsonText, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <K, V> Map<K, V> toMap(String jsonText, Class<K> keyClass, Class<V> valueClass) {
        if (StringUtils.isEmpty(jsonText)) {
            return null;
        }

        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(Map.class, keyClass, valueClass);
            return mapper.readValue(jsonText, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
