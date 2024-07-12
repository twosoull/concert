package io.hhplus.concert.infrastructure.repositoryORM;

import io.hhplus.concert.domain.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenJpaRepository extends JpaRepository<Token,Long> {

    @Query("select t from Token t where t.user.id = :userId")
    Token findByUserId(@Param("userId") Long userId);
}
