package io.hhplus.concert.infrastructure.repositoryORM;

import io.hhplus.concert.domain.entity.ConcertSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertSchedule,Long> {
}
