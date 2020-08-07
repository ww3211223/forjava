package com.nonono.test._springboot.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

@Service
public class LoginService {

    private HttpServletRequest currentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public Integer getUserId() {
        Cookie[] cookies = this.currentRequest().getCookies();
        Cookie cookie = Arrays.stream(cookies).filter(s -> Objects.equals(s.getName(), "userId")).findFirst().orElse(null);
        return cookie == null ? null : Integer.valueOf(cookie.getValue());
    }
}
