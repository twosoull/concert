package io.hhplus.concert.domain.respository;

import io.hhplus.concert.domain.entity.Wallet;

public interface WalletRepository {
    Wallet findByUserId(Long userId);
}
