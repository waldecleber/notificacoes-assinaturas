package com.waldecleber.globoplay.subscription.model;

import com.waldecleber.globoplay.subscription.config.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

import static javax.transaction.Transactional.TxType.MANDATORY;

public class AuditSubscriptionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditSubscriptionListener.class);

    @PrePersist
    public void onPrePersist(Subscription subscription) {
        audit(Type.INSERT, subscription);
        LOGGER.info(String.format("Inserted subscription %s", subscription.getId()));
    }

    @PreUpdate
    public void onPreUpdate(Subscription subscription) {
        audit(Type.UPDATE, subscription);
        LOGGER.info(String.format("Updated subscription %s", subscription.getId()));
    }

    @Transactional(MANDATORY)
    private void audit(Type type, Subscription subscription) {
        EventHistory eventHistory = EventHistory.builder()
                .type(type)
                .subscriptionId(subscription)
                .build();

        EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
        entityManager.persist(eventHistory);
    }

}
