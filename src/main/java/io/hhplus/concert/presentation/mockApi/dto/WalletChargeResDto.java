package io.hhplus.concert.presentation.mockApi.dto;

import java.time.LocalDateTime;

public class WalletChargeResDto {

    private Long amount;
    private Long balanceBefore;
    private Long balanceAfter;
    private Long balance;
    private LocalDateTime transactionDate;

    public WalletChargeResDto(Long amount, Long balanceBefore, Long balanceAfter, Long balance, LocalDateTime transactionDate) {
        this.amount = amount;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        this.balance = balance;
        this.transactionDate = transactionDate;
    }
}
