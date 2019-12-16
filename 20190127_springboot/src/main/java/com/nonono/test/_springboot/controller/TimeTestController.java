package com.nonono.test._springboot.controller;

import com.nonono.test._springboot.model.JsonDataTime;
import com.nonono.test._springboot.util.JacksonUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/time-test")
public class TimeTestController {

    @RequestMapping(value = "/time")
    public LocalDateTime time(@RequestParam("time") LocalDateTime time) {
        return time;
    }

    @RequestMapping(value = "/now")
    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    @RequestMapping(value = "/jsonDateTime")
    public LocalDateTime jsonDateTime(@RequestBody JsonDataTime jsonDataTime) {
        return jsonDataTime.getTime();
    }

    @RequestMapping(value = "/jsonUtils")
    public JsonDataTime jsonUtils() {
        String timeStr = "{\"id\":12,\"time\":\"2019-12-16 14:22:11\"}";
        return JacksonUtils.generic().toObj(timeStr, JsonDataTime.class);
    }
}
