package io.hhplus.concert.infrastructure.repositoryImpl;

import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.domain.entity.ConcertSeat;
import io.hhplus.concert.domain.handler.exception.RestApiException;
import io.hhplus.concert.domain.respository.ConcertSeatRepository;
import io.hhplus.concert.infrastructure.repositoryORM.ConcertSeatJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode.RESOURCE_NOT_FOUND;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ConcertSeatRepositoryImpl implements ConcertSeatRepository {

    private final ConcertSeatJpaRepository concertSeatJpaRepository;

    @Override
    public List<ConcertSeat> findAllByConcertScheduleIdAndStatus(Long concertScheduleId, SeatStatus status) {
        return concertSeatJpaRepository.findAllByConcertScheduleIdAndStatus(concertScheduleId, status);
    }

    @Override
    public ConcertSeat findById(Long concertSeatId) {
        return concertSeatJpaRepository.findById(concertSeatId).orElseThrow(
                () -> new RestApiException(RESOURCE_NOT_FOUND)
        );
    }

    @Override
    public ConcertSeat findByIdWithOptimisticLock(Long concertSeatId) {
        try {
            return concertSeatJpaRepository.findByIdWithOptimisticLock(concertSeatId).orElseThrow(
                    () -> new RuntimeException("ConcertSeat with id " + concertSeatId + " not found")
            );
        } catch (Exception e) {
            log.error("Exception occurred while finding ConcertSeat with optimistic lock: ", e);
            throw e;
        }
    }

    @Override
    public ConcertSeat findByIdWithPessimisticLock(Long concertSeatId) {
        return concertSeatJpaRepository.findByIdWithPessimisticLock(concertSeatId).orElseThrow(
                () -> new RestApiException(RESOURCE_NOT_FOUND)
        );
    }

    public ConcertSeat save(ConcertSeat concertSeat) {
        return concertSeatJpaRepository.save(concertSeat);
    }
}
