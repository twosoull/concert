package io.hhplus.concert.domain.service;

import io.hhplus.concert.ConcertApplication;
import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.common.utils.RequestTokenUtil;
import io.hhplus.concert.domain.command.PaymentCommand;
import io.hhplus.concert.domain.entity.*;
import io.hhplus.concert.domain.respository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ConcertApplication.class)
@Slf4j
class OptimisticConCurrencyPaymentServiceTest {


    @Autowired
    private PaymentService paymentService;

    @MockBean
    private RequestTokenUtil requestTokenUtil;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private ConcertScheduleRepository concertScheduleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConcertSeatRepository concertSeatRepository;

    @Autowired
    private ConcertReservationRepository concertReservationRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("차감 요청이 동시에 여러개 들어 와도 1개만 처리한다.")
    void pay_test() throws InterruptedException {

        Long userId = 1L;
        when(requestTokenUtil.getCurrentTokenUserId()).thenReturn(userId);

        Long concertId = 1L;
        Long concertSeatId = 1L;
        Long concertScheduleId = 1L;
        LocalDateTime now = LocalDateTime.now();
        User user = userRepository.findById(userId);
        ConcertSchedule concertSchedule = concertScheduleRepository.findById(concertScheduleId);

        ConcertReservation saveReservation = getConcertReservation(concertSeatId, concertSchedule, user, now);

        int numberOfUsers = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfUsers);
        CountDownLatch latch = new CountDownLatch(numberOfUsers);
        ConcurrentLinkedDeque<Integer> failedQueue = new ConcurrentLinkedDeque<>();
        for (int i = 0; i < numberOfUsers; i++) {
            int finalI = i;

            executorService.execute(() -> {
                try {
                    PaymentCommand.Pay pay = new PaymentCommand.Pay(concertScheduleId, saveReservation.getId(), concertSeatId);
                    paymentService.pay(pay);
                } catch (OptimisticLockException e) {
                    failedQueue.add(finalI);
                    log.error("Exception during reservation: ", e);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executorService.shutdown();

        Assertions.assertThat(failedQueue.size()).isEqualTo(numberOfUsers - 1);

        // 차감이 정상적으로 처리 되었는지 확인

    }

    @Transactional
    private ConcertReservation getConcertReservation(Long concertSeatId, ConcertSchedule concertSchedule, User user, LocalDateTime now) {
        ConcertSeat concertSeat = concertSeatRepository.findById(concertSeatId);
        concertSeat.setSeatStatusTemp(concertSchedule, user, now);

        // 이후 로직
        ConcertReservation concertReservation = ConcertReservation.createReserveStatusTemp(concertSchedule, user, now);
        ConcertReservation saveReservation = concertReservationRepository.save(concertReservation);
        return saveReservation;
    }

}