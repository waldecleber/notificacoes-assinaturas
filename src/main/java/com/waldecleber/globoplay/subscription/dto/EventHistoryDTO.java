package com.waldecleber.globoplay.subscription.dto;

import java.time.OffsetDateTime;
import java.util.Objects;

public class EventHistoryDTO {

    private Integer id;
    private String type;
    private SubscriptionDTO subscriptionId;
    private OffsetDateTime createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SubscriptionDTO getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(SubscriptionDTO subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventHistoryDTO that = (EventHistoryDTO) o;
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
        private String type;
        private SubscriptionDTO subscriptionId;
        private OffsetDateTime createdAt;

        private Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder subscriptionId(SubscriptionDTO subscriptionId) {
            this.subscriptionId = subscriptionId;
            return this;
        }

        public Builder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public EventHistoryDTO build() {
            EventHistoryDTO eventHistory = new EventHistoryDTO();
            eventHistory.setId(id);
            eventHistory.setType(type);
            eventHistory.setSubscriptionId(subscriptionId);
            eventHistory.setCreatedAt(createdAt);
            return eventHistory;
        }
    }
}
