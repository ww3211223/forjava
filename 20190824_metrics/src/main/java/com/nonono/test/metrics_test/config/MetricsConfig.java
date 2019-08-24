package com.nonono.test.metrics_test.config;

import com.codahale.metrics.*;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class MetricsConfig {

    @Bean
    public MetricRegistry metrics() {
        return new MetricRegistry();
    }

    @Bean
    public ConsoleReporter consoleReporter(MetricRegistry metricRegistry) {
        return ConsoleReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
    }

    @Bean
    public Slf4jReporter slf4jReporter(MetricRegistry metricRegistry) {
        return Slf4jReporter.forRegistry(metricRegistry)
                .outputTo(LoggerFactory.getLogger("demo.metrics"))
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
    }

    /**
     * TPS 计算器
     *
     * @param metricRegistry
     * @return
     */
    @Bean
    public Meter requestMeter(MetricRegistry metricRegistry) {
        return metricRegistry.meter("request");
    }

    /**
     * 柱状图
     *
     * @param metricRegistry
     * @return
     */
    @Bean
    public Histogram responseSizes(MetricRegistry metricRegistry) {
        return metricRegistry.histogram("response-size");
    }

    /**
     * 计数器
     *
     * @param metricRegistry
     * @return
     */
    @Bean
    public Counter pendingJobs(MetricRegistry metricRegistry) {
        return metricRegistry.counter("requestCount");
    }

    /**
     * 计时器
     *
     * @param metricRegistry
     * @return
     */
    @Bean
    public Timer responses(MetricRegistry metricRegistry) {
        return metricRegistry.timer("executerTime");
    }
}
