package io.hhplus.concert.domain.respository;


import io.hhplus.concert.domain.entity.Concert;

public interface ConcertRepository {

    Concert save(Concert concert);

    Concert findById(Long id);
}
