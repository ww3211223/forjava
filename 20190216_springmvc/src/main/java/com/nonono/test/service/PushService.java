package com.nonono.test.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

@Service
public class PushService {

    private DeferredResult<String> deferredResult;

    public DeferredResult<String> GetAsyncUpdate() {
        deferredResult = new DeferredResult<String>();
        return deferredResult;
    }

    @Scheduled(fixedDelay = 5000)
    public void refreash() {
        if (deferredResult != null) {
            deferredResult.setResult(new Long(System.currentTimeMillis()).toString());
        }
    }

}
