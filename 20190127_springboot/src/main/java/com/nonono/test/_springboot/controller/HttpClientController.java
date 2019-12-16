package com.nonono.test._springboot.controller;

import com.alibaba.fastjson.JSON;
import com.nonono.test._springboot.model.DataResult;
import com.nonono.test._springboot.model.JsonDataTime;
import com.nonono.test._springboot.util.JacksonUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * httpClient测试控制器
 */
@RestController
@RequestMapping(value = "/http-client")
public class HttpClientController {

    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/now")
    public LocalDateTime now() {
        return restTemplate.getForObject("http://localhost:3310/time-test/now", LocalDateTime.class);
    }

    @RequestMapping("/time")
    public LocalDateTime time(@RequestParam("time") LocalDateTime time) {
        LocalDateTime result = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost:3310")
                    .setPath("/time-test/time")
                    .setParameter("time", time.toString())
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpGet request = new HttpGet(uri);
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(request, HttpClientContext.create());
            HttpEntity entity = httpResponse.getEntity();
            String content = EntityUtils.toString(entity, "UTF-8");
            result = JSON.parseObject(content, LocalDateTime.class);
            httpResponse.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping("/header")
    public String header(HttpServletRequest httpRequest) {
        return httpRequest.getHeaders("api-version").nextElement();
    }

    @RequestMapping("/sendHeader/{version}")
    public String sendHeader(@PathVariable("version") Integer version) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("api-version", version.toString());
        org.springframework.http.HttpEntity<MultiValueMap> entity = new org.springframework.http.HttpEntity<>(null, requestHeaders);
        return restTemplate.postForObject("http://localhost:3310/http-client/header", entity, String.class);
    }

    @RequestMapping("/toJsonList")
    public List<JsonDataTime> toJsonList(@RequestBody String context) {
        return JacksonUtils.generic().json2List(context);
    }

    @RequestMapping("/toJsonList2")
    public List<JsonDataTime> toJsonList2(@RequestBody String context) {
        return JacksonUtils.generic().json2List2(context, JsonDataTime.class);
    }

    @RequestMapping("/toJsonList3")
    public DataResult<List<JsonDataTime>> toJsonList3(@RequestBody String context) {
        return JacksonUtils.generic().toNestRefObj(context, DataResult.class, List.class, JsonDataTime.class);
    }

    @RequestMapping("/toJsonList4")
    public DataResult<List<JsonDataTime>> toJsonList4(@RequestBody String context) {
        //使用fastjson
        return JSON.parseObject(context, DataResult.class);
    }

}
