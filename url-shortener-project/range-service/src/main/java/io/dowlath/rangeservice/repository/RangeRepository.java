package io.dowlath.rangeservice.repository;

import io.dowlath.rangeservice.entity.Range;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface RangeRepository extends JpaRepository<Range, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<Range> findTopBy();
}