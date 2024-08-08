package io.hhplus.concert.infrastructure.repositoryORM;

import io.hhplus.concert.domain.entity.WalletHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletHistoryJpaRepository extends JpaRepository<WalletHistory, Long> {

}
