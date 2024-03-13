package com.n11.apigateway.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mehmet Akif Tanisik
 */
@Configuration
public class RabbitMqConfig {


    @Bean
    Queue scoreUpdaterDql() {
        return QueueBuilder.durable("gateway.logging.dlq").build();
    }

    @Bean
    DirectExchange scoreUpdaterDlqExchange() {
        return new DirectExchange("gateway.logging.dlq.exchange");
    }
    @Bean
    Binding scoreUpdaterDlqBinding() {
        return BindingBuilder.bind(scoreUpdaterDql())
                .to(scoreUpdaterDlqExchange())
                .with("gateway.logging.dlq.routing-key");
    }

    @Bean
    Queue scoreUpdaterQueue() {
        return QueueBuilder
                .durable("gateway.logging.queue")
                .withArgument("x-dead-letter-exchange", "gateway.logging.dlq.exchange")
                .withArgument("x-dead-letter-routing-key", "gateway.logging.dlq.routing-key").build();
    }

    @Bean
    DirectExchange scoreUpdaterQueueExchange() {
        return new DirectExchange("gateway.logging.queue.exchange");
    }

    @Bean
    Binding scoreUpdaterQueueBinding() {
        return BindingBuilder.bind(scoreUpdaterQueue())
                .to(scoreUpdaterQueueExchange())
                .with("gateway.logging.queue.routing-key");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
