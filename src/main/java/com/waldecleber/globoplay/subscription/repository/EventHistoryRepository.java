package com.waldecleber.globoplay.subscription.repository;

import com.waldecleber.globoplay.subscription.model.EventHistory;
import com.waldecleber.globoplay.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventHistoryRepository extends JpaRepository<EventHistory, Integer> {
}
