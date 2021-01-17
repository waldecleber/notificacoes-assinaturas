package com.waldecleber.globoplay.subscription.service;

import com.waldecleber.globoplay.subscription.config.SubscriptionAMQPConfig;
import com.waldecleber.globoplay.subscription.dto.SubscriptionDTO;
import com.waldecleber.globoplay.subscription.exceptions.SubscriptionWithoutStatusException;
import com.waldecleber.globoplay.subscription.model.Subscription;
import com.waldecleber.globoplay.subscription.repository.SubscriptionRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class SubscriptionService {

    private RabbitTemplate rabbitTemplate;
    private SubscriptionRepository repository;
    private ModelMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionService.class);

    public SubscriptionService(SubscriptionRepository repository, ModelMapper mapper, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.mapper = mapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public SubscriptionDTO save(SubscriptionDTO dto) {
        if (Optional.ofNullable(dto.getStatusId()).isPresent()) {
            dto.setCreatedAt(OffsetDateTime.now());
            Subscription subscription = mapper.map(dto, Subscription.class);
            repository.save(subscription);
            LOGGER.info(String.format("Subscription saved: %s", subscription.getId()));
            return dto;
        } else {
            String errorMsg = "Cannot save a subscription without status";
            LOGGER.error(errorMsg);
            throw new SubscriptionWithoutStatusException(errorMsg);
        }
    }

    public void sendSubscriptionToRabbit(SubscriptionDTO subscription) {
        LOGGER.info(String.format("Send a subscription %s to queue.", subscription.getId()));
        rabbitTemplate.convertAndSend(SubscriptionAMQPConfig.EXCHANGE_NAME, "", subscription);
    }

}
