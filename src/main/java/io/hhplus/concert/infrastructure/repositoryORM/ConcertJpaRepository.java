package io.hhplus.concert.infrastructure.repositoryORM;

import io.hhplus.concert.domain.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpaRepository extends JpaRepository<Concert,Long> {
}
