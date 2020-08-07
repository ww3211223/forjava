package com.nonono.test._springboot.controller;

import com.nonono.test._springboot.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    private LoginService loginService;

    public Integer getUserId() {
        return loginService.getUserId();
    }
}
