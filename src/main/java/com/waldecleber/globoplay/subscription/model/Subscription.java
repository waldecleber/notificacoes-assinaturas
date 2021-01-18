package com.waldecleber.globoplay.subscription.model;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "subscription")
@EntityListeners(AuditListener.class)
public class Subscription {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name="status_id")
    private Status status;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        Subscription that = (Subscription) o;
        return Objects.equals(id, that.id)
                && Objects.equals(status, that.status)
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, createdAt, updatedAt);
    }

    @PrePersist
    public void onPrePersist() {
        setCreatedAt(OffsetDateTime.now());
    }

    @PreUpdate
    public void onPreUpdate() {
        setUpdatedAt(OffsetDateTime.now());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private Status status;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder statusId(Status statusId) {
            this.status = statusId;
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

        public Subscription build() {
            Subscription subscription = new Subscription();
            subscription.setId(id);
            subscription.setStatus(status);
            subscription.setCreatedAt(createdAt);
            subscription.setUpdatedAt(updatedAt);
            return subscription;
        }
    }
}
