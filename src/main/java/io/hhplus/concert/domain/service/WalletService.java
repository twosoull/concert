package io.hhplus.concert.domain.service;

import io.hhplus.concert.common.enums.TransactionType;
import io.hhplus.concert.domain.command.WalletCommand;
import io.hhplus.concert.domain.entity.Wallet;
import io.hhplus.concert.domain.entity.WalletHistory;
import io.hhplus.concert.domain.respository.WalletHistoryRepository;
import io.hhplus.concert.domain.respository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletHistoryRepository walletHistoryRepository;

    public WalletCommand.balanceInfoResDto balanceInfo(WalletCommand.balanceInfoReqDto reqDto) {
        Wallet wallet = walletRepository.findByUserId(reqDto.userId());
        if(ObjectUtils.isEmpty(wallet)){
            throw new RuntimeException("잘못된 요청 입니다.");
        }

        return WalletCommand.walletOf(wallet);
    }

    public WalletCommand.chargeResDto charge(WalletCommand.chargeReqDto reqDto) {
        Wallet wallet = walletRepository.findByUserId(reqDto.userId());

        Long balanceBefore = wallet.getBalance();
        Long amount = reqDto.amount();
        LocalDateTime now = LocalDateTime.now();

        Long balanceAfter = addBalance(balanceBefore, amount);
        wallet.update(balanceAfter, now);

        WalletHistory walletHistory = walletHistoryRepository
                .save(getWalletHistory(wallet, amount, balanceBefore, balanceAfter, now));

        return WalletCommand.walletHistoryOf(walletHistory);
    }

    public static Long addBalance(Long balanceBefore, Long amount) {
        if(balanceBefore < 0){
            throw new RuntimeException("잘못된 요청 입니다.");
        }

        if(amount < 0){
            throw new RuntimeException("잘못된 요청 입니다.");
        }

        return balanceBefore + amount;
    }

    private static WalletHistory getWalletHistory(Wallet wallet, Long amount, Long balanceBefore, Long balanceAfter, LocalDateTime now) {
        WalletHistory walletHistory = WalletHistory.chargeHistory(
                wallet
                , amount
                , balanceBefore
                , balanceAfter
                , now
        );
        return walletHistory;
    }


}
