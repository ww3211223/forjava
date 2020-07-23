package com.nonono.test.cloud.server.controller;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/test")
public class TestController {

    private final Logger logger = Logger.getLogger(TestController.class.getName());

    private static List<String> RESULT = Lists.newArrayList("加达里", "米玛塔尔", "盖伦特", "艾玛");

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("test host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
        Random random = new Random();
        return RESULT.get(random.nextInt(RESULT.size()));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String single(@PathVariable("id") Integer id) {
        logger.info(String.format("single id#%s", id));
        if (id == null || id <= 0 || id > RESULT.size()) {
            return null;
        }

        return RESULT.get(id - 1);
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public List<String> list(@RequestBody List<Integer> ids) {
        logger.info(String.format("list ids#%s", ids));
        List<String> result = Lists.newArrayList();
        if (CollectionUtils.isEmpty(ids)) {
            return result;
        }

        for (Integer id : ids) {
            if (id == null || id <= 0 || id > RESULT.size()) {
                continue;
            }

            result.add(RESULT.get(id - 1));
        }

        return result;
    }

    @RequestMapping(value = "/fallback", method = RequestMethod.GET)
    public String fallback() {
        int sleepTime = new Random().nextInt(3000);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "server-result";
    }
}
