package com.nonono.test.rabbitmq.customer;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMQConf {

    @Value("${spring.rabbitmq.test.listener.concurrency}")
    private Integer concurrency;

    @Value("${spring.rabbitmq.test.listener.max-concurrency}")
    private Integer maxConcurrency;

    @Bean(name = "connectionFactory")
    @Primary
    public ConnectionFactory connectionFactory(@Value("${spring.rabbitmq.test.host}") String host,
                                               @Value("${spring.rabbitmq.test.port}") Integer port,
                                               @Value("${spring.rabbitmq.test.username}") String username,
                                               @Value("${spring.rabbitmq.test.password}") String password,
                                               @Value("${spring.rabbitmq.test.virtual-host}") String vhost) {
        CachingConnectionFactory connFactory = new CachingConnectionFactory();
        connFactory.setHost(host);
        connFactory.setPort(port);
        connFactory.setUsername(username);
        connFactory.setPassword(password);
        connFactory.setVirtualHost(vhost);

        return connFactory;
    }

    @Bean(name = "listenerContainerFactory")
    public SimpleRabbitListenerContainerFactory listenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, @Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConcurrentConsumers(concurrency);
        factory.setMaxConcurrentConsumers(maxConcurrency);
        configurer.configure(factory, connectionFactory);

        return factory;
    }

    @Bean(name = "connectionFactory2")
    public ConnectionFactory connectionFactory2(@Value("${spring.rabbitmq.test2.host}") String host,
                                               @Value("${spring.rabbitmq.test2.port}") Integer port,
                                               @Value("${spring.rabbitmq.test2.username}") String username,
                                               @Value("${spring.rabbitmq.test2.password}") String password,
                                               @Value("${spring.rabbitmq.test2.virtual-host}") String vhost) {
        CachingConnectionFactory connFactory = new CachingConnectionFactory();
        connFactory.setHost(host);
        connFactory.setPort(port);
        connFactory.setUsername(username);
        connFactory.setPassword(password);
        connFactory.setVirtualHost(vhost);

        return connFactory;
    }

    @Bean(name = "listenerContainerFactory2")
    public SimpleRabbitListenerContainerFactory listenerContainerFactory2(SimpleRabbitListenerContainerFactoryConfigurer configurer, @Qualifier("connectionFactory2") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConcurrentConsumers(concurrency);
        factory.setMaxConcurrentConsumers(maxConcurrency);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        configurer.configure(factory, connectionFactory);

        return factory;
    }

}
