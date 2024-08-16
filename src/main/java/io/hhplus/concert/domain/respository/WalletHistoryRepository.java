package io.hhplus.concert.domain.respository;

import io.hhplus.concert.domain.entity.WalletHistory;

public interface WalletHistoryRepository {
    WalletHistory save(WalletHistory walletHistory);
}
