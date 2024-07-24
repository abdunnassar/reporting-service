package com.nasserusmantv.reporting.repository;

import com.nasserusmantv.reporting.entity.ClaimEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends JpaRepository<ClaimEntity, Long> {

    @Query(value = "SELECT COUNT(*) FROM claim WHERE created_date >= CURRENT_TIMESTAMP - INTERVAL '1' DAY", nativeQuery = true)
    long countClaimsCreatedInLast24Hours();

    @Query(value = "SELECT COUNT(*) FROM claim WHERE last_update_date >= CURRENT_TIMESTAMP - INTERVAL '1' DAY", nativeQuery = true)
    long countClaimsUpdatedInLast24Hours();
}

