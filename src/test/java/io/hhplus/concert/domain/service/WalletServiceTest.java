package io.hhplus.concert.domain.service;

import io.hhplus.concert.domain.command.WalletCommand;
import io.hhplus.concert.domain.entity.User;
import io.hhplus.concert.domain.entity.Wallet;
import io.hhplus.concert.domain.entity.WalletHistory;
import io.hhplus.concert.domain.respository.WalletHistoryRepository;
import io.hhplus.concert.domain.respository.WalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @InjectMocks
    WalletService walletService;

    @Mock
    WalletRepository walletRepository;

    @Mock
    WalletHistoryRepository walletHistoryRepository;

    @Test
    @DisplayName("사용자 잔고 조회 테스트")
    void balanceInfo() {
        //given
        Long walletId = 1L;
        Long userId = 1L;
        Long balance = 10000L;

        User user = new User(userId, "asfasd", "name");
        Wallet wallet = new Wallet(walletId, user, balance);

        doReturn(wallet).when(walletRepository).findByUserId(any(Long.class));

        //when
        Wallet findWallet = walletService.getBalanceInfo();

        //then
        assertEquals(walletId,findWallet.getId());
        assertEquals(balance,findWallet.getBalance());
    }

    @Test
    @DisplayName("사용자 잔고 조회 테스트 - 조회 정보 없음")
    void balanceInfo_null() {
        //given
        Long userId = 1L;

        //when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            walletService.getBalanceInfo();
        });

        // 예외 메시지 검증
        assertEquals("잘못된 요청 입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("사용자 잔고 충전")
    void charge_test() {
        //given
        Long walletId = 1L;
        Long userId = 1L;
        Long balance = 10000L;

        User user = new User(userId, "asfasd", "name");
        Wallet wallet = new Wallet(walletId, user, balance);

        doReturn(wallet).when(walletRepository).findByUserId(any(Long.class));

        Long amount = 20000L;
        Long balanceAfter = balance + amount;
        LocalDateTime now = LocalDateTime.now();
        WalletHistory walletHistory = WalletHistory.chargeHistory(
                wallet
                , amount
                , balance
                , balanceAfter
                , now
        );
        doReturn(walletHistory).when(walletHistoryRepository).save(any(WalletHistory.class));

        WalletCommand.GetChargeInfo chargeReqDto = new WalletCommand.GetChargeInfo(userId, amount);
        WalletHistory chargeWallet = walletService.chargeWallet(chargeReqDto);

        assertEquals(balanceAfter, chargeWallet.getBalanceAfter());
    }

    @Test
    @DisplayName("잔고 충전 테스트 - balanceBefore 음수")
    void addBalance_test_balanceBefore_minus(){

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            walletService.addBalance(-1L,10000L);
        });

        // 예외 메시지 검증
        assertEquals("잘못된 요청 입니다.", exception.getMessage());

    }

    @Test
    @DisplayName("잔고 충전 테스트 - amount 음수")
    void addBalance_test_amount_minus(){

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            walletService.addBalance(10000L,-20L);
        });

        // 예외 메시지 검증
        assertEquals("잘못된 요청 입니다.", exception.getMessage());

    }
}