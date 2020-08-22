package com.nonono.test.ame.core.reflect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LittleType implements ParameterizedType {

    private final Type[] actualTypeArguments;
    private final Class<?> rawType;
    private final Type ownerType;

    public static LittleType tiny(Type actualTypeArguments, Class<?> rawType) {
        return new LittleType(new Type[]{actualTypeArguments}, rawType, null);
    }

    public static LittleType create(Class<?> rawType, Type... actualTypeArguments) {
        Type[] types2 = (Type[]) Arrays.stream(actualTypeArguments).toArray();
        return new LittleType(types2, rawType, null);
    }

}
