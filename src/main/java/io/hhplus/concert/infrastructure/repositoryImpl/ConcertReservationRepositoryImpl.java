package io.hhplus.concert.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.entity.ConcertReservation;
import io.hhplus.concert.domain.respository.ConcertReservationRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ConcertReservationRepositoryImpl implements ConcertReservationRepository {
    @Override
    public ConcertReservation save(ConcertReservation concertReservation) {
        return null;
    }

    @Override
    public ConcertReservation findById(Long aLong) {
        return null;
    }
}
