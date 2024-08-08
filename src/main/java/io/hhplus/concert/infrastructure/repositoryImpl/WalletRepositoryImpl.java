package io.hhplus.concert.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.entity.Wallet;
import io.hhplus.concert.domain.respository.WalletRepository;
import io.hhplus.concert.infrastructure.repositoryORM.WalletJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WalletRepositoryImpl implements WalletRepository {

    private final WalletJpaRepository walletJpaRepository;

    @Override
    public Wallet findByUserId(Long userId) {
        return walletJpaRepository.findByUserId(userId);
    }

    @Override
    public Wallet findByUserIdWithOptimisticLock(Long userId) {
        return walletJpaRepository.findByUserIdWithOptimisticLock(userId);
    }

    @Override
    public Wallet save(Wallet wallet) {
        return walletJpaRepository.save(wallet);
    }
}
