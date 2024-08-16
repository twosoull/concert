package io.hhplus.concert.presentation.dto;

import io.hhplus.concert.domain.command.WalletCommand;
import io.hhplus.concert.domain.entity.Wallet;
import io.hhplus.concert.domain.entity.WalletHistory;

import java.time.LocalDateTime;

public class WalletDto {
    public record BalanceInfoRequest(Long userId){}
    public record BalanceInfoResponse(Long userWalletId, Long userId, Long balance){}
    public record ChargeRequest(Long userId, Long amount){}
    public record ChargeResponse(Long amount, Long balanceBefore, Long balance, LocalDateTime transactionDate){}

    public static WalletCommand.GetChargeInfo chargeToCommand(WalletDto.ChargeRequest chargeRequest){
        return new WalletCommand.GetChargeInfo(
                chargeRequest.userId(),
                chargeRequest.amount()
        );
    }

    public static WalletDto.BalanceInfoResponse balanceInfoResponseOf(Wallet wallet) {
        return new WalletDto.BalanceInfoResponse(wallet.getId(),
                wallet.getUser().getId()
                ,wallet.getBalance());
    }

    public static WalletDto.ChargeResponse ChargeResponseOf(WalletHistory walletHistory) {
        return new WalletDto.ChargeResponse(walletHistory.getAmount()
                ,walletHistory.getBalanceBefore()
                ,walletHistory.getBalanceAfter()
                , walletHistory.getTransactionDate());
    }

}
