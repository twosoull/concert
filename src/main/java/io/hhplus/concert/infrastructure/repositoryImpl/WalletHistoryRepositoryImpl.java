package io.hhplus.concert.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.entity.WalletHistory;
import io.hhplus.concert.domain.respository.WalletHistoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class WalletHistoryRepositoryImpl implements WalletHistoryRepository {
    @Override
    public WalletHistory save(WalletHistory walletHistory) {
        return null;
    }
}
