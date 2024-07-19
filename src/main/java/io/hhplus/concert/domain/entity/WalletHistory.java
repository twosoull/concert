package io.hhplus.concert.domain.entity;

import io.hhplus.concert.common.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class WalletHistory {

    @Id @GeneratedValue
    @Column(name = "wallet_history_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    private Long amount;
    private TransactionType transactionType; //거래 유형

    private Long balanceBefore;
    private Long balanceAfter;
    private LocalDateTime transactionDate; //거래일자



    public static WalletHistory chargeHistory(Wallet wallet, Long amount, Long balanceBefore, Long balanceAfter, LocalDateTime transactionDate){
        return WalletHistory.builder()
                .wallet(wallet)
                .amount(amount)
                .transactionType(TransactionType.CHARGE)
                .balanceBefore(balanceBefore)
                .balanceAfter(balanceAfter)
                .transactionDate(transactionDate)
                .build();
    }

    public static WalletHistory useHistory(Wallet wallet, Long amount,Long balanceBefore, Long balanceAfter, LocalDateTime transactionDate){
        return WalletHistory.builder()
                .wallet(wallet)
                .amount(amount)
                .transactionType(TransactionType.USE)
                .balanceBefore(balanceBefore)
                .balanceAfter(balanceAfter)
                .transactionDate(transactionDate)
                .build();
    }
}
