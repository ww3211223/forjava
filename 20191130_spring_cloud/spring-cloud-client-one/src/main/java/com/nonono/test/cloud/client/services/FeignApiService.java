package com.nonono.test.cloud.client.services;

import com.nonono.test.spring.cloud.client.api.FeignServiceClient;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "nonono-cloud-service", fallback = FeignFallbackService.class)
public interface FeignApiService extends FeignServiceClient {
}
