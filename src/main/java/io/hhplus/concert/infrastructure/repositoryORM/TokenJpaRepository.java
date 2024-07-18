package io.hhplus.concert.infrastructure.repositoryORM;

import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.command.TokenCommand;
import io.hhplus.concert.domain.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TokenJpaRepository extends JpaRepository<Token,Long> {

    @Query("select t from Token t where t.user.id = :userId")
    Token findByUserId(@Param("userId") Long userId);

    @Query("select t from Token t where t.status = :status")
    List<Token> findAllByStatus(@Param("status") TokenStatus status);

    @Query("select t from Token t where t.user.id = :userId and t.status = :status")
    Token findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") TokenStatus status);

    @Query("select t from Token t where t.status = 'WAIT' order by id asc limit 1")
    Token findTopStatusWait();

    @Query(name = "Token.findByActiveTokenAndCountAndTopId", nativeQuery = true)
    TokenCommand.CheckTokenResultDto findByActiveTokenAndCountAndTopId();

    @Modifying
    @Query(value="UPDATE TOKEN SET status = 'ACTIVE' WHERE token_id BETWEEN :firstId AND :lastId", nativeQuery = true)
    int updateTokenStatusActive(@Param("firstId") Long firstId, @Param("lastId") Long lastId);

    @Query("select t from Token t where t.token = :token")
    Token findByToken(@Param("token") String token);
}
