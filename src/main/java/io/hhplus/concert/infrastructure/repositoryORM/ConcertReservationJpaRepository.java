package io.hhplus.concert.infrastructure.repositoryORM;

import io.hhplus.concert.domain.entity.ConcertReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertReservationJpaRepository extends JpaRepository<ConcertReservation, Long> {
}
