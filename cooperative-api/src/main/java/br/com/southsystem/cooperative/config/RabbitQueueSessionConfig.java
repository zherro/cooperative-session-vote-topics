package br.com.southsystem.cooperative.config;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public class RabbitQueueSessionConfig {

    private static final Logger log = LoggerFactory.getLogger(RabbitQueueSessionConfig.class);

    public static final String SESSION_QUEUE = "cooperative.amqp.queue";
    public static final String SESSION_QUEUE_DLQ = SESSION_QUEUE + ".dlq";
    public static final String SESSION_QUEUE_DELAYED = SESSION_QUEUE + ".delayed";
    public static final String SESSION_QUEUE_EXCHANGE = SESSION_QUEUE + ".exchange";
    public static final String SESSION_QUEUE_EXCHANGE_DELAYED = SESSION_QUEUE + ".delayed.exchange";;

    private final RabbitAdmin rabbitAdmin;

    private final RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    @Autowired
    public RabbitQueueSessionConfig(RabbitAdmin rabbitAdmin,
            RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry) {
        this.rabbitAdmin = rabbitAdmin;
        this.rabbitListenerEndpointRegistry = rabbitListenerEndpointRegistry;
    }

    @PostConstruct
    public void constructRabbitComponents( ) {
        try {
            log.info("m=constructRabbitComponents, creating queues rabbit");
            createQueue(SESSION_QUEUE, SESSION_QUEUE_EXCHANGE, SESSION_QUEUE_DLQ);
            createDlq(SESSION_QUEUE_DLQ);
            createQueue(SESSION_QUEUE_DELAYED, SESSION_QUEUE_EXCHANGE, SESSION_QUEUE);

            createExchange(SESSION_QUEUE_EXCHANGE);
            createExchange(SESSION_QUEUE_EXCHANGE_DELAYED);

            // QUEUE
            biding(SESSION_QUEUE, SESSION_QUEUE_EXCHANGE);

            // DQL
            biding(SESSION_QUEUE_DLQ, SESSION_QUEUE_EXCHANGE);

            // DELAYED
            biding(SESSION_QUEUE_DELAYED, SESSION_QUEUE_EXCHANGE_DELAYED);
        } catch (Exception e) {
            log.warn("m=constructRabbitComponents, oh no!!! fail to attempt create rabbit queues automaticaly, "
                    + "verify your profile configuration and restart the application.");
//
//            // para os listeners para não ficar gerando logs
//            // utilizado apenas por ser um projeto de testes
//            rabbitListenerEndpointRegistry.getListenerContainer("sessionConsumer").stop();
        }
    }

    public void createQueue(String queue, String exchange, String dql) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(queue).deadLetterExchange(exchange)
                .deadLetterRoutingKey(dql).build());
    }

    public void createDlq(String queue) {
        rabbitAdmin.declareQueue(QueueBuilder
                .durable(queue).build());
    }

    public void createExchange(String exchange) {
        rabbitAdmin.declareExchange(ExchangeBuilder
                .directExchange(exchange).build());
    }

    public void biding(String queue, String exchange) {
        rabbitAdmin.declareBinding(
                new Binding(
                        queue, Binding.DestinationType.QUEUE,
                        exchange, queue, null
                )
        );
    }
}
