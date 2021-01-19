package com.waldecleber.globoplay.subscription.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "event_history")
@EntityListeners(AuditingEntityListener.class)
public class EventHistory {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventHistory.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscriptionId;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Subscription getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Subscription subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventHistory that = (EventHistory) o;
        return Objects.equals(id, that.id)
                && Objects.equals(type, that.type)
                && Objects.equals(subscriptionId, that.subscriptionId)
                && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, subscriptionId, createdAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer id;
        private Type type;
        private Subscription subscriptionId;
        private LocalDateTime createdAt;

        private Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        public Builder subscriptionId(Subscription subscriptionId) {
            this.subscriptionId = subscriptionId;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public EventHistory build() {
            EventHistory eventHistory = new EventHistory();
            eventHistory.setId(id);
            eventHistory.setType(type);
            eventHistory.setSubscriptionId(subscriptionId);
            eventHistory.setCreatedAt(createdAt);
            return eventHistory;
        }
    }
}
