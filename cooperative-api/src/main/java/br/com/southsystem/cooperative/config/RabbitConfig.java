package br.com.southsystem.cooperative.config;

import javax.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@ConfigurationProperties(prefix = "spring.rabbit")
@Setter
@Configuration
public class RabbitConfig {

    public static final String SESSION_QUEUE = "cooperative.amqp.queue";
    public static final String SESSION_QUEUE_DLQ = SESSION_QUEUE + ".dlq";
    public static final String SESSION_QUEUE_DELAYED = SESSION_QUEUE + ".delayed";
    public static final String SESSION_QUEUE_EXCHANGE = SESSION_QUEUE + ".queue.exchange";
    public static final String SESSION_QUEUE_EXCHANGE_DELAYED = SESSION_QUEUE + ".delayed.exchange";;


    private String host;
    private int port;
    private String username;
    private String password;


    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate(final Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    @Primary
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        var connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(this.host);
        connectionFactory.setUsername(this.username);
        connectionFactory.setPassword(this.password);
        connectionFactory.setPort(this.port);
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    /**
     * Usado para conversão automática de objetos para JSON
     */
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean("sessionManagerFactory")
    public RabbitListenerContainerFactory sessionManagerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrentConsumers(2);
        factory.setMaxConcurrentConsumers(2);
        factory.setDefaultRequeueRejected(false);
        return factory;
    }
}
