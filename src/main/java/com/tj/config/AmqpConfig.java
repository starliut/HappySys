package com.tj.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    @Bean
    public MessageConverter jackson2JsonMessageConverter(){

        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Exchange createExchange(){
        return new DirectExchange("direct.happy.insert_foot_print");
    }

    @Bean
    public Queue createQueue(){
        return new Queue("queue.insert_foot_print",true);
    }

    @Bean
    public Binding createBinding(){
        return new Binding("queue.insert_foot_print",Binding.DestinationType.QUEUE,"direct.happy.insert_foot_print","queue.insert_foot_print",null);
    }



}
