package com.nonono.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
public class SseController {

    @RequestMapping(value = "/push", produces = "text/event-stream")
    public @ResponseBody
    String push() {
        Random random = new Random();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        return "data:Testing 1,2,3" + random.nextInt() + "\n\n";
    }

}
