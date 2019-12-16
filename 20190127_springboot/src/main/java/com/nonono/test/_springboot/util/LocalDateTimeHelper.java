package com.nonono.test._springboot.util;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * localDateTime帮助类
 */
public class LocalDateTimeHelper {

    /**
     * String 转换为 LocalDateTime
     *
     * @param time
     * @return
     */
    public static LocalDateTime parse(String time) {
        if (StringUtils.isEmpty(time)) {
            return null;
        }
        if (time.indexOf("T") > -1) {
            return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        }
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
