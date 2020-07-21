package com.nonono.test.cloud.client.services;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

/**
 * 测试被观察者命令
 */
public class TestObservableCommand extends HystrixObservableCommand<String> {
    private RestTemplate restTemplate;

    public TestObservableCommand(RestTemplate restTemplate) {
        //super(HystrixCommandGroupKey.Factory.asKey("testCommand"));
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("testCommand"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(3000)));
        this.restTemplate = restTemplate;
    }

    @Override
    protected Observable<String> construct() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String body = restTemplate.getForEntity("http://nonono-cloud-service/test/", String.class).getBody();
                    subscriber.onNext(body);
                    subscriber.onNext("second next.");
                    subscriber.onNext("third next.");
                    subscriber.onCompleted();
                } catch (Exception ex) {
                    subscriber.onError(ex);
                }
            }
        });
    }
}
