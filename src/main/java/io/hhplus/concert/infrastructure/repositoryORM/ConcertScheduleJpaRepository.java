package io.hhplus.concert.infrastructure.repositoryORM;

import io.hhplus.concert.domain.entity.ConcertSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertSchedule,Long> {

    List<ConcertSchedule> findByConcertId(@Param("concertId")Long concertId);
}
