package com.nonono.test._springboot.util;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.ParameterizedType;

@Getter
@Builder
@AllArgsConstructor
public class CustomTypeReference extends TypeReference<Object> {

    private ParameterizedType type;

}
