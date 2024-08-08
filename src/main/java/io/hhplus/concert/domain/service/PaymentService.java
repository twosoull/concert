package io.hhplus.concert.domain.service;

import io.hhplus.concert.common.utils.RequestTokenUtil;
import io.hhplus.concert.domain.command.PaymentCommand;
import io.hhplus.concert.domain.entity.*;
import io.hhplus.concert.domain.handler.exception.TokenException;
import io.hhplus.concert.domain.respository.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode.RESOURCE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final WalletRepository walletRepository;
    private final WalletHistoryRepository walletHistoryRepository;
    private final ConcertReservationRepository concertReservationRepository;
    private final ConcertSeatRepository concertSeatRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RequestTokenUtil requestTokenUtil;
    private final EntityManager entityManager;
    private final RedisRepository redisRepository;

    @Transactional
    public PaymentCommand.getPaymentInfo pay(PaymentCommand.Pay pay) {
        validToken();
        LocalDateTime now = LocalDateTime.now();
        Long currentTokenUserId = requestTokenUtil.getCurrentTokenUserId();

        //예약했던 콘서트일정 상태를 확정으로 만들어 준다.
        ConcertReservation concertReservation = concertReservationRepository.findById(pay.concertReservationId());
        concertReservation.setReserved();

        usePointWithOptimisticLock result = getUsePointWithOptimisticLock(currentTokenUserId, concertReservation, now);

        // 히스토리를 쌓는다.
        walletHistoryRepository.save(getWalletHistory(result.wallet(), result.price(), result.balanceBefore(), result.balanceAfter(), now));


        // 콘서트 자리 확정으로 해준다.
        ConcertSeat concertSeat = concertSeatRepository.findById(pay.seatId());
        concertSeat.seatStatusAssign(now);

        return new PaymentCommand.getPaymentInfo(concertReservation.getConcertTitle(),
                                                concertReservation.getDescription(),
                                                concertReservation.getConcertAt(),
                                                concertReservation.getStatus(),
                                                result.price(),
                                                result.balanceAfter(),
                                                result.wallet().getLastUpdateAt(),
                                                concertSeat.getSeat(),
                                                concertSeat.getStatus());
    }

    private usePointWithOptimisticLock getUsePointWithOptimisticLock(Long userId, ConcertReservation concertReservation, LocalDateTime now) {
        try {
            Wallet wallet = walletRepository.findByUserIdWithOptimisticLock(userId);
            //콘서트 금액과 잔고의 금액을 마이너스 해준다.
            Long price = concertReservation.getPrice();
            Long balanceBefore = wallet.getBalance();
            Long balanceAfter = wallet.usePoint(price, now);

            //lock 적용을 위한 flush
            entityManager.flush();

            usePointWithOptimisticLock result = new usePointWithOptimisticLock(wallet, price, balanceBefore, balanceAfter);
            return result;
        } catch (ObjectOptimisticLockingFailureException e) {

            log.error("Optimistic locking {}", e);
            throw new RuntimeException("Failed to acquire optimistic lock", e);
        }
    }

    private record usePointWithOptimisticLock(Wallet wallet, Long price, Long balanceBefore, Long balanceAfter) {
    }


    private static WalletHistory getWalletHistory(Wallet wallet, Long amount, Long balanceBefore,Long balanceAfter, LocalDateTime now) {
        WalletHistory walletHistory = WalletHistory.useHistory(
                wallet
                , amount
                , balanceBefore
                , balanceAfter
                , now
        );
        return walletHistory;
    }

    private void validToken() {
        String currentToken = requestTokenUtil.getCurrentToken();
        boolean isActive = redisRepository.isActiveMember(currentToken);

        //액티브에 없으면
        if (!isActive) {
            throw new TokenException(RESOURCE_NOT_FOUND);
        }
    }
}
