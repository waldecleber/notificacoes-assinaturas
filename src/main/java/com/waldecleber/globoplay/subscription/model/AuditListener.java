package com.waldecleber.globoplay.subscription.model;

import com.waldecleber.globoplay.subscription.config.ApplicationContextProvider;
import com.waldecleber.globoplay.subscription.repository.EventHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.OffsetDateTime;

public class AuditListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditListener.class);

    @PrePersist
    public void onPrePersist(Subscription subscription) {
        audit("INSERT", subscription);
        LOGGER.info(subscription.getId());
    }

    @PreUpdate
    public void onPreUpdate(Subscription subscription) {
        audit("UPDATE", subscription);
        LOGGER.info(subscription.getId());
    }

    private void audit(String operation, Subscription subscription) {
        EventHistory eventHistory = new EventHistory();
        eventHistory.setType(operation);
        eventHistory.setCreatedAt(OffsetDateTime.now());
        eventHistory.setSubscriptionId(subscription);

        EventHistoryRepository eventRepository
                = (EventHistoryRepository) ApplicationContextProvider.getBean("eventHistoryRepository");
        eventRepository.save(eventHistory);
    }

}
