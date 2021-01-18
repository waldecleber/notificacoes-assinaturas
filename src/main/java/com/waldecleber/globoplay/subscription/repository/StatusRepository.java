package com.waldecleber.globoplay.subscription.repository;

import com.waldecleber.globoplay.subscription.model.Status;
import com.waldecleber.globoplay.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
    Optional<Status> findByName(String name);
}
