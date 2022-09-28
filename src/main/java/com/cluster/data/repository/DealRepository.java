package com.cluster.data.repository;

import com.cluster.data.domain.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {

    Optional<Deal> getDealByUniqueId(UUID uniqueId);
}
