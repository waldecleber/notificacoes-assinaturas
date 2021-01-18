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
import org.springframework.util.StringUtils;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class SubscriptionService {

    public static final String CANNOT_SAVE_A_SUBSCRIPTION_WITHOUT_STATUS = "Cannot save a subscription without status";
    public static final String SEND_A_SUBSCRIPTION_TO_QUEUE = "Send a subscription %s to queue.";
    public static final String SUBSCRIPTION_SAVED = "Subscription saved: %s";
    public static final String STATUS_NOT_FOUND = "Status not found.";
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionService.class);

    private RabbitTemplate rabbitTemplate;
    private SubscriptionRepository repository;
    private StatusRepository statusRepository;
    private ModelMapper mapper;

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

            LOGGER.info(String.format(SUBSCRIPTION_SAVED, subscription.getId()));
            return dto;
        } else {
            LOGGER.error(CANNOT_SAVE_A_SUBSCRIPTION_WITHOUT_STATUS);
            throw new SubscriptionWithoutStatusException(CANNOT_SAVE_A_SUBSCRIPTION_WITHOUT_STATUS);
        }
    }

    private Status loadStatus(StatusDTO status) {
        return statusRepository.findByName(status.getName())
                .orElseThrow(() -> new StatusNotFoundException(STATUS_NOT_FOUND));
    }

    public void sendSubscriptionToRabbit(SubscriptionDTO dto) {
        if (Optional.ofNullable(dto.getStatus()).isPresent()) {
            LOGGER.info(String.format(SEND_A_SUBSCRIPTION_TO_QUEUE, dto.getId()));
            rabbitTemplate.convertAndSend(SubscriptionAMQPConfig.EXCHANGE_NAME, "", dto);
        } else {
            LOGGER.error(CANNOT_SAVE_A_SUBSCRIPTION_WITHOUT_STATUS);
            throw new SubscriptionWithoutStatusException(CANNOT_SAVE_A_SUBSCRIPTION_WITHOUT_STATUS);
        }
    }

}
