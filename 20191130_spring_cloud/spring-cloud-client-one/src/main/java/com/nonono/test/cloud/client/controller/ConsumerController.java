package com.nonono.test.cloud.client.controller;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.nonono.test.cloud.client.services.ConsumerService;
import com.nonono.test.cloud.client.services.TestCollapseCommand;
import com.nonono.test.cloud.client.services.TestObservableCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

@RestController
public class ConsumerController {

    private final Logger logger = Logger.getLogger(ConsumerController.class.getName());

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/ribbon-consumer", method = RequestMethod.GET)
    public String consumer() {
        return consumerService.consumer();
    }

    @RequestMapping(value = "/has-fallback", method = RequestMethod.GET)
    public String hasFallbackTest() {
        return consumerService.hasFallbackTest();
    }

    @RequestMapping(value = "/observable-command", method = RequestMethod.GET)
    public String observableCommand() {
        TestObservableCommand command = new TestObservableCommand(restTemplate);
        Observable<String> observe = command.observe();

        observe.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                logger.info("observable completed.");
            }

            @Override
            public void onError(Throwable throwable) {
                logger.info("observable error.");
            }

            @Override
            public void onNext(String s) {
                logger.info(String.format("observable next. result:#%s", s));
            }
        });

        return "success";
    }

    @RequestMapping(value = "/collapse/{id}", method = RequestMethod.GET)
    public String collapses(@PathVariable("id") Integer id) {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        String result = consumerService.findById(id);
        context.close();
        return result;

//        HystrixRequestContext context = HystrixRequestContext.initializeContext();
//        TestCollapseCommand testCollapseCommand = new TestCollapseCommand(restTemplate, id);
//        String result = testCollapseCommand.execute();
//        context.close();
//        return result;
    }
}
