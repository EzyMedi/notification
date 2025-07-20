package com.EzyMedi.notification.config;



import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NewsMessageConfiguration {
    @Value("${spring.rabbitmq.exchange.news}")
    private String newsExchange;
    @Value("${spring.rabbitmq.queue.news}")
    private String newsQueue;
    @Bean
    public Queue notificationQueue() {
        return new Queue(newsQueue);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(newsExchange);
    }

    @Bean
    public Binding binding(Queue notificationQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(notificationQueue).to(fanoutExchange);
    }

}
