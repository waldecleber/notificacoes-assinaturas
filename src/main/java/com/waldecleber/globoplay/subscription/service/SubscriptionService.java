package com.waldecleber.globoplay.subscription.service;

import com.waldecleber.globoplay.subscription.config.SubscriptionAMQPConfig;
import com.waldecleber.globoplay.subscription.dto.StatusDTO;
import com.waldecleber.globoplay.subscription.dto.SubscriptionDTO;
import com.waldecleber.globoplay.subscription.exceptions.StatusNotFoundException;
import com.waldecleber.globoplay.subscription.exceptions.SubscriptionWithoutStatusException;
import com.waldecleber.globoplay.subscription.model.Status;
import com.waldecleber.globoplay.subscription.model.Subscription;
import com.waldecleber.globoplay.subscription.repository.StatusRepository;
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
    private StatusRepository statusRepository;
    private ModelMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionService.class);

    public SubscriptionService(SubscriptionRepository repository, StatusRepository statusRepository,
                               ModelMapper mapper, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.statusRepository = statusRepository;
        this.mapper = mapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    public SubscriptionDTO save(SubscriptionDTO dto) {
        if (Optional.ofNullable(dto.getStatus()).isPresent()) {
            Subscription subscription = mapper.map(dto, Subscription.class);
            subscription.setStatus(loadStatus(dto.getStatus()));
            repository.save(subscription);

            LOGGER.info(String.format("Subscription saved: %s", subscription.getId()));
            return dto;
        } else {
        String errorMsg = "Cannot save a subscription without status";
        LOGGER.error(errorMsg);
        throw new SubscriptionWithoutStatusException(errorMsg);
    }
    }

    private Status loadStatus(StatusDTO status) {
        return statusRepository.findByName(status.getName())
                .orElseThrow(() -> new StatusNotFoundException("Status not found."));
    }

    public void sendSubscriptionToRabbit(SubscriptionDTO dto) {
        if (Optional.ofNullable(dto.getStatus()).isPresent()) {
            LOGGER.info(String.format("Send a subscription %s to queue.", dto.getId()));
            rabbitTemplate.convertAndSend(SubscriptionAMQPConfig.EXCHANGE_NAME, "", dto);
        } else {
            String errorMsg = "Cannot save a subscription without status";
            LOGGER.error(errorMsg);
            throw new SubscriptionWithoutStatusException(errorMsg);
        }
    }

}
