package com.obeast.chat.config;

import com.obeast.chat.utils.RabbitMQUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author wxl
 * Date 2022/12/27 13:06
 * @version 1.0
 * Description:
 */
@Configuration
public class RabbitMqConfig {

    @Value("${netty.port}")
    private Integer port;

    @Bean
    public Queue queue(){
        return new Queue(RabbitMQUtils.QUEUE_NAME + port);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(RabbitMQUtils.EXCHANGE_NAME);
    }

    @Bean
    public Binding queueToExchange () {
        return BindingBuilder
                .bind(queue())
                .to(fanoutExchange());
    }
}
