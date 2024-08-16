package io.hhplus.concert.domain.respository;

import io.hhplus.concert.domain.entity.ConcertReservation;

public interface ConcertReservationRepository {

    ConcertReservation save(ConcertReservation concertReservation);

    ConcertReservation findById(Long aLong);
}
