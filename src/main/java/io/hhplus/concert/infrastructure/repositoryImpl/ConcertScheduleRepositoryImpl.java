package io.hhplus.concert.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.entity.ConcertSchedule;
import io.hhplus.concert.domain.respository.ConcertScheduleRepository;
import io.hhplus.concert.infrastructure.repositoryORM.ConcertJpaRepository;
import io.hhplus.concert.infrastructure.repositoryORM.ConcertScheduleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConcertScheduleRepositoryImpl implements ConcertScheduleRepository {

    private final ConcertScheduleJpaRepository concertScheduleJpaRepository;

    @Override
    public ConcertSchedule save(ConcertSchedule concertSchedule) {
        return concertScheduleJpaRepository.save(concertSchedule);
    }

    @Override
    public ConcertSchedule findById(Long concertScheduleId) {
        return concertScheduleJpaRepository.findById(concertScheduleId).orElseThrow(
                ()->new RuntimeException("조회가 불가합니다.")
        );
    }
}
