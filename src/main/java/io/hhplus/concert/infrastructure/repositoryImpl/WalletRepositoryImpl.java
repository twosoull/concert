package io.hhplus.concert.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.entity.Wallet;
import io.hhplus.concert.domain.respository.WalletRepository;
import org.springframework.stereotype.Repository;

@Repository
public class WalletRepositoryImpl implements WalletRepository {
    @Override
    public Wallet findByUserId(Long userId) {
        return null;
    }
}
