package com.waldecleber.globoplay.subscription.dto;

import java.time.OffsetDateTime;
import java.util.Objects;

public class SubscriptionDTO {

    private String id;
    private StatusDTO statusId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StatusDTO getStatusId() {
        return statusId;
    }

    public void setStatusId(StatusDTO statusId) {
        this.statusId = statusId;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionDTO that = (SubscriptionDTO) o;
        return Objects.equals(id, that.id)
                && Objects.equals(statusId, that.statusId)
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, statusId, createdAt, updatedAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private StatusDTO statusId;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder statusId(StatusDTO statusId) {
            this.statusId = statusId;
            return this;
        }

        public Builder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public SubscriptionDTO build() {
            SubscriptionDTO subscription = new SubscriptionDTO();
            subscription.setId(id);
            subscription.setStatusId(statusId);
            subscription.setCreatedAt(createdAt);
            subscription.setUpdatedAt(updatedAt);
            return subscription;
        }
    }
}
