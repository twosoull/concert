package io.hhplus.concert.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.entity.Concert;
import io.hhplus.concert.domain.respository.ConcertRepository;
import io.hhplus.concert.infrastructure.repositoryORM.ConcertJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {

    private final ConcertJpaRepository concertJpaRepository;

    @Override
    public Concert save(Concert concert) {
        return concertJpaRepository.save(concert);
    }

    @Override
    public Concert findById(Long id) {
        return concertJpaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("잘못된 요청 입니다.")
        );
    }
}
