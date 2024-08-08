package io.hhplus.concert.domain.service;

import io.hhplus.concert.common.utils.RequestTokenUtil;
import io.hhplus.concert.domain.command.WalletCommand;
import io.hhplus.concert.domain.entity.Token;
import io.hhplus.concert.domain.entity.Wallet;
import io.hhplus.concert.domain.entity.WalletHistory;
import io.hhplus.concert.domain.handler.exception.RestApiException;
import io.hhplus.concert.domain.handler.exception.WalletException;
import io.hhplus.concert.domain.respository.WalletHistoryRepository;
import io.hhplus.concert.domain.respository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode.NEGATIVE_VALUE;
import static io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode.RESOURCE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletHistoryRepository walletHistoryRepository;
    private final RequestTokenUtil requestTokenUtil;

    public Wallet getBalanceInfo() {
        Long currentTokenUserId = requestTokenUtil.getCurrentTokenUserId();
        Wallet wallet = walletRepository.findByUserId(currentTokenUserId);
        if(ObjectUtils.isEmpty(wallet)){
            throw new RestApiException(RESOURCE_NOT_FOUND);
        }

        return wallet;
    }

    public WalletHistory chargeWallet(WalletCommand.GetChargeInfo getChargeInfo) {
        Wallet wallet = walletRepository.findByUserId(getChargeInfo.userId());

        Long balanceBefore = wallet.getBalance();
        Long amount = getChargeInfo.amount();
        LocalDateTime now = LocalDateTime.now();

        Long balanceAfter = addBalance(balanceBefore, amount);
        wallet.update(balanceAfter, now);

        WalletHistory walletHistory = walletHistoryRepository
                .save(getWalletHistory(wallet, amount, balanceBefore, balanceAfter, now));

        return walletHistory;
    }

    public static Long addBalance(Long balanceBefore, Long amount) {
        if(balanceBefore < 0){
            throw new WalletException(NEGATIVE_VALUE);
        }

        if(amount < 0){
            throw new WalletException(NEGATIVE_VALUE);
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
