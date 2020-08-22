package com.nonono.test.ame.core.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public final class EnumHelper {

    private static final Logger logger = LoggerFactory.getLogger(EnumHelper.class);

    public static final Map<Class<? extends CodeDescriptionFeature>, Map<Integer, ? extends CodeDescriptionFeature>> map = Maps.newHashMap();

    /**
     * 注册枚举类 对应的 所有枚举值
     *
     * @param enumClass
     */
    public static void register(Class<? extends CodeDescriptionFeature> enumClass) {
        CodeDescriptionFeature[] features = enumClass.getEnumConstants();
        if (ArrayUtils.isEmpty(features)) {
            return;
        }
        Map<Integer, CodeDescriptionFeature> mm = Arrays.stream(features).collect(Collectors.toMap(CodeDescriptionFeature::getCode, x -> x));
        map.put(enumClass, mm);
    }

    /**
     * 从<code>code</code>, 获取枚举实例
     *
     * @param code
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends CodeDescriptionFeature> T valueOf(Integer code, Class<T> enumClass) {
        if (map.containsKey(enumClass)) {
            return (T) map.get(enumClass).get(code);
        }
        register(enumClass);
        return (T) map.get(enumClass).get(code);
    }

    /**
     * 从<code>code</code>, 获取枚举实例的描述, 如果不存在, 返回空字符串
     *
     * @param code
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends CodeDescriptionFeature> String getDescription(Integer code, Class<T> enumClass) {
        return getDescription(code, enumClass, "");
    }

    /**
     * 从<code>code</code>, 获取枚举实例的描述, 如果不存在, 返回<code>defaultValue</code>
     *
     * @param code
     * @param enumClass
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T extends CodeDescriptionFeature> String getDescription(Integer code, Class<T> enumClass, String defaultValue) {
        CodeDescriptionFeature enu = valueOf(code, enumClass);
        return enu != null ? enu.getDescription() : defaultValue;
    }


    /**
     * 获取一个枚举类型的所有的枚举值
     *
     * @param enumClass
     * @return
     */
    public static <T> List<T> getEnums(Class<T> enumClass) {
        return Arrays.asList(enumClass.getEnumConstants());
    }

    /**
     * 获取枚举和描述map
     *
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends CodeDescriptionFeature> Map<Integer, String> getEnumDescList(Class<T> enumClass) {
        Map<Integer, String> maps = new HashMap<>();

        List<T> enums = getEnums(enumClass);
        if (enums == null || enums.isEmpty()) {
            return maps;
        }

        for (T em : enums) {
            maps.put(em.getCode(), em.getDescription());
        }
        return maps;
    }

    /**
     * 兼容, , 如果不存在, 返回空字符串
     *
     * @param code
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends CodeDescriptionFeature> String GetDescription(Integer code, Class<T> enumClass) {
        return getDescription(code, enumClass, "");
    }


    /**
     * 数字, 文本值(不区分大小写), 统统给你转换成 枚举
     *
     * @param source
     * @param enumType
     * @param <T>
     * @return
     */
    public static <T extends Enum> T textToEnum(String source, Class<T> enumType) {
        if (StringUtils.isBlank(source)) {
            return null;
        }

        if (!enumType.isEnum()) {
            throw new RuntimeException("class[" + enumType + "] MUST be enum-class");
        }

        source = StringUtils.trimToEmpty(source);

        if (CodeDescriptionFeature.class.isAssignableFrom(enumType)) {
            if (NumberUtils.isDigits(source)) {
                return (T) EnumHelper.valueOf(Integer.valueOf(source), (Class<? extends CodeDescriptionFeature>) enumType);
            }
        }

        T[] enumValues = enumType.getEnumConstants();
        for (int i = enumValues.length; --i >= 0; ) {
            T e = enumValues[i];
            if (StringUtils.equalsIgnoreCase(e.toString(), source)) {
                return e;
            }
        }

        if (NumberUtils.isDigits(source)) {
            int value = Integer.valueOf(source);

            // 兼容处理 - 枚举类型没有实现 CodeDescriptionFeature, 但是有 value 或 code 属性(int 或 Integer)
            T legacyEnum = digitsToLegacyEnum(value, enumType);
            if (legacyEnum != null) {
                return legacyEnum;
            }

            // 最终依然没有匹配到, 把 source 当做枚举的 index
            if (value < enumValues.length) {
                return enumValues[value];
            }
        }

        throw new RuntimeException("no suitable enum for text[" + source + "] in enum-class[" + enumType + "]");
    }

    private static final Map<Class<? extends Enum>, Map<Integer, ?>> legacyEnums = Maps.newHashMap();

    private static <T extends Enum> T digitsToLegacyEnum(int value, Class<T> enumType) {
        Map<Integer, ?> enums = legacyEnums.get(enumType);
        if (enums == null) {
            enums = legacyEnumToMap(enumType);
            legacyEnums.put(enumType, enums);
        }
        return (T) enums.get(value);
    }

    private static <T extends Enum> Map<Integer, T> legacyEnumToMap(Class<T> enumType) {
        Field[] fields = enumType.getDeclaredFields();

        if (ArrayUtils.isEmpty(fields)) {
            return Collections.emptyMap();
        }

        Field valueField = null;
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())
                    && (field.getType() == int.class || field.getType() == Integer.class)) {
                valueField = field;
                break;
            }
        }

        if (valueField == null) {
            return Collections.emptyMap();
        }

        logger.info("enumType#{} with field#{} for legacy json deserializer", enumType, valueField);

        Map<Integer, T> res = Maps.newHashMap();
        T[] enumValues = enumType.getEnumConstants();
        for (int i = enumValues.length; --i >= 0; ) {
            T e = enumValues[i];
            try {
                valueField.setAccessible(true);
                int v = ((Number) valueField.get(e)).intValue();
                res.put(v, e);
            } catch (Exception e1) {
                logger.error("", e1);
            }
        }

        return res;
    }

}
