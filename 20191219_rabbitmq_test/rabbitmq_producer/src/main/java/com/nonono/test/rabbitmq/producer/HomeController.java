package com.nonono.test.rabbitmq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private RabbitMQSender mqSender;

    @RequestMapping("/testSend")
    public String testSend(@RequestParam("msg") String msg) {
        mqSender.send("nonono.test.ex", "rk.test", msg);
        return "发送完成";
    }

    @RequestMapping("/testSendOne")
    public String testSendOne(@RequestParam("msg") String msg) {
        mqSender.send("nonono.test.ex", "rk.test.one", msg);
        return "发送完成";
    }

    @RequestMapping("/testSendTwo")
    public String testSendTwo(@RequestParam("msg") String msg) {
        mqSender.send("nonono.test.ex", "rk.test.two", msg);
        return "发送完成";
    }
}
