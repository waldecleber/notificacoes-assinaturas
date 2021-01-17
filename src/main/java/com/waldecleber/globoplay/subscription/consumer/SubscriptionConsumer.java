package com.waldecleber.globoplay.subscription.consumer;

import com.waldecleber.globoplay.subscription.config.SubscriptionAMQPConfig;
import com.waldecleber.globoplay.subscription.dto.SubscriptionDTO;
import com.waldecleber.globoplay.subscription.model.Subscription;
import com.waldecleber.globoplay.subscription.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionConsumer {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private SubscriptionService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionConsumer.class);


    @RabbitListener(queues = SubscriptionAMQPConfig.QUEUE)
    public void consumer(SubscriptionDTO subscription) {
        LOGGER.info(String.format("Consuming the subscription %s", subscription.getId()));
        service.save(subscription);
//        simpMessagingTemplate.convertAndSend(SubscriptionWebSocketConfig.BROKER,
//                new String(message.getBody()));
    }

}
