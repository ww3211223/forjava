package com.nonono.test._springboot.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 自定义参数化类型
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class CustomParameterizedType implements ParameterizedType {
    private final Type[] actualTypeArguments;
    private final Class<?> rawType;
    private final Type ownerType;

    public static CustomParameterizedType create(Type actualTypeArguments, Class<?> rawType) {
        return new CustomParameterizedType(new Type[]{actualTypeArguments}, rawType, null);
    }
}
