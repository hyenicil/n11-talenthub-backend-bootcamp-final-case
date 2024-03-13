package com.n11.reviewservice.config;

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
        return QueueBuilder.durable("score.updater.dlq").build();
    }

    @Bean
    DirectExchange scoreUpdaterDlqExchange() {
        return new DirectExchange("score.updater.dlq.exchange");
    }
    @Bean
    Binding scoreUpdaterDlqBinding() {
        return BindingBuilder.bind(scoreUpdaterDql())
                .to(scoreUpdaterDlqExchange())
                .with("score.updater.dlq.routing-key");
    }

    @Bean
    Queue scoreUpdaterQueue() {
        return QueueBuilder
                .durable("score.updater.queue")
                .withArgument("x-dead-letter-exchange", "score.updater.dlq.exchange")
                .withArgument("x-dead-letter-routing-key", "score.updater.dlq.routing-key").build();
    }

    @Bean
    DirectExchange scoreUpdaterQueueExchange() {
        return new DirectExchange("score.updater.queue.exchange");
    }

    @Bean
    Binding scoreUpdaterQueueBinding() {
        return BindingBuilder.bind(scoreUpdaterQueue())
                .to(scoreUpdaterQueueExchange())
                .with("score.updater.queue.routing-key");
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
