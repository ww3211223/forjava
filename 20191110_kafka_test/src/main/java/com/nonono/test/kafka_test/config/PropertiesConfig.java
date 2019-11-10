package com.nonono.test.kafka_test.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "nonono.kafka")
public class PropertiesConfig {

    /**
     * 连接地址
     */
    private String url;

    /**
     * GroupId
     */
    private String groupId;

    /**
     * 是否自动提交
     */
    private Boolean hasAutoCommit;

    /**
     * 自动提交间隔
     */
    private Integer autoCommitInterval;

    /**
     * Session超时
     */
    private Integer sessionTimeOut;

    /**
     * 是否重试
     */
    private Integer retries;

    /**
     * 批处理大小，单位为字节
     */
    private Integer batchSize;

    /**
     * 批量发送延时
     */
    private Integer lingerMessage;

    /**
     * 生产者缓冲大小
     */
    private Long bufferMemory;
}
