package io.hhplus.concert.domain.command;

import io.hhplus.concert.domain.entity.Wallet;
import io.hhplus.concert.domain.entity.WalletHistory;

import java.time.LocalDateTime;

public class WalletCommand {

    public record balanceInfoReqDto(Long userId){}
    public record balanceInfoResDto(Long userWalletId, Long userId, Long balance){}
    public record chargeReqDto(Long userId, Long amount){}
    public record chargeResDto(Long amount, Long balanceBefore, Long balance, LocalDateTime transactionDate){}

    public static WalletCommand.balanceInfoResDto walletOf(Wallet wallet) {
        return new balanceInfoResDto(wallet.getId(),
                wallet.getUser().getId()
                ,wallet.getBalance());
    }

    public static WalletCommand.chargeResDto walletHistoryOf(WalletHistory walletHistory) {
        return new WalletCommand.chargeResDto(walletHistory.getAmount()
                ,walletHistory.getBalanceBefore()
                ,walletHistory.getBalanceAfter()
                , walletHistory.getTransactionDate());
    }

}
