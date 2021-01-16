package com.waldecleber.globoplay.subscription.service;

import com.waldecleber.globoplay.subscription.config.SubscriptionAMQPConfig;
import com.waldecleber.globoplay.subscription.dto.SubscriptionDTO;
import com.waldecleber.globoplay.subscription.model.Subscription;
import com.waldecleber.globoplay.subscription.repository.SubscriptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class SubscriptionService {

    private RabbitTemplate rabbitTemplate;
    private SubscriptionRepository repository;
    private ModelMapper mapper;

    public SubscriptionService(SubscriptionRepository repository, ModelMapper mapper, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.mapper = mapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public SubscriptionDTO save(SubscriptionDTO dto) {
        dto.setCreatedAt(OffsetDateTime.now());

        Subscription subscription = mapper.map(dto, Subscription.class);
        repository.save(subscription);

        sendSubscriptionToRabbit(subscription);
        return dto;
    }

    public void sendSubscriptionToRabbit(Subscription subscription) {
        rabbitTemplate.convertAndSend(SubscriptionAMQPConfig.EXCHANGE_NAME, "", subscription.getId());
    }

}
