package com.EzyMedi.notification.config;



import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // This bean ensures your listener uses Jackson2JsonMessageConverter for deserialization
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        return factory;
    }

    @Bean
    public Binding binding(Queue notificationQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(notificationQueue).to(fanoutExchange);
    }

}
