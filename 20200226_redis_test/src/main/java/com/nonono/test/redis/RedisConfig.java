package com.nonono.test.redis;


import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;


@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);

        GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //redisTemplate.setDefaultSerializer(redisSerializer);
        redisTemplate.setValueSerializer(new RedisSerializer<Object>() {
            @Override
            public byte[] serialize(Object o) throws SerializationException {
                if (o == null) {
                    return new byte[0];
                }
                if (!(o instanceof Serializable)) {
                    throw new IllegalArgumentException("RedisSerializer.serialize requires a Serializable payload "
                            + "but received an object of type [" + o.getClass().getName() + "]");
                }

                return SerializationUtils.serialize(o);
            }

            @Override
            public Object deserialize(byte[] bytes) throws SerializationException {
                if (bytes == null || bytes.length == 0) {
                    return null;
                }

                return SerializationUtils.deserialize(bytes);
            }
        });


        return redisTemplate;
    }

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer();

        String addr = "redis://" + host + ":" + port;
        serverConfig.setAddress(addr);

        if (StringUtils.isNotBlank(password)) {
            serverConfig.setPassword(password);
        }

        return Redisson.create(config);
    }

    @Bean
    public TestRedissonLock redissonLockTest() {
        return new TestRedissonLock(redissonClient());
    }

}
