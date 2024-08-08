package io.hhplus.concert.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.entity.WalletHistory;
import io.hhplus.concert.domain.respository.WalletHistoryRepository;
import io.hhplus.concert.infrastructure.repositoryORM.WalletHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WalletHistoryRepositoryImpl implements WalletHistoryRepository {

    private final WalletHistoryJpaRepository walletHistoryJpaRepository;

    @Override
    public WalletHistory save(WalletHistory walletHistory) {
        return walletHistoryJpaRepository.save(walletHistory);
    }
}
