package io.hhplus.concert.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.entity.ConcertReservation;
import io.hhplus.concert.domain.handler.exception.RestApiException;
import io.hhplus.concert.domain.respository.ConcertReservationRepository;
import io.hhplus.concert.infrastructure.repositoryORM.ConcertReservationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode.RESOURCE_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class ConcertReservationRepositoryImpl implements ConcertReservationRepository {

    private final ConcertReservationJpaRepository concertReservationJpaRepository;

    @Override
    public ConcertReservation save(ConcertReservation concertReservation) {
        return concertReservationJpaRepository.save(concertReservation);
    }

    @Override
    public ConcertReservation findById(Long concertReservationId) {
        return concertReservationJpaRepository.findById(concertReservationId).orElseThrow(
                () -> new RestApiException(RESOURCE_NOT_FOUND)
        );
    }
}
