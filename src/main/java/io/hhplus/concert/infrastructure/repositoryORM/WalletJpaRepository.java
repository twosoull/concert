package io.hhplus.concert.infrastructure.repositoryORM;

import io.hhplus.concert.domain.entity.Wallet;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WalletJpaRepository extends JpaRepository<Wallet, Long> {

    @Query("select w from Wallet w where w.user.id = :userId")
    Wallet findByUserId(@Param("userId") Long userId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select w from Wallet w where w.user.id = :userId")
    Wallet findByUserIdWithOptimisticLock(@Param("userId") Long userId);
}
