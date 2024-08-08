package io.hhplus.concert.domain.service;

import io.hhplus.concert.ConcertApplication;
import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.common.utils.RequestTokenUtil;
import io.hhplus.concert.domain.command.ReservationCommand;
import io.hhplus.concert.domain.entity.*;
import io.hhplus.concert.domain.respository.*;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ConcertApplication.class)
@Slf4j
class OptimisticConCurrencyReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

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

    int successCnt = 0;

    @Test
    @DisplayName("한 자리에 대해 동시에 여러 요청이 들어 와도 1개의 요청만 자리를 임시 예약한다.")
    void reserve_test() throws InterruptedException {
        Long concertSeatId = 1L;

        int numberOfUsers = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfUsers);
        CountDownLatch latch = new CountDownLatch(numberOfUsers);
        ConcurrentLinkedDeque<Integer> failedQueue = new ConcurrentLinkedDeque<>();

        for (int i = 0; i < numberOfUsers; i++) {
            int finalI = i;
            Long userId = (long) (i + 1);
            // Mocking the token utility to return a user ID
            when(requestTokenUtil.getCurrentTokenUserId()).thenReturn(userId);

            // 쓰레드 5개 던지면 5개 성공??
            executorService.execute(() -> {
                try {
                    reservationService.reserve(new ReservationCommand.reserve(1L, 1L, 1L, "OPTIMISTIC"));
                    successCnt +=1;
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

        // Mock한 리포지토리에서 resultSeat를 가져올 수 있도록 설정
        ConcertSeat resultSeat = concertSeatRepository.findById(concertSeatId);

        // 예약이 정상적으로 처리되었는지 검증 로직 추가
        //assertNotNull(resultSeat);
        assertEquals(SeatStatus.TEMP, resultSeat.getStatus());  // 예시 검증 로직
        assertTrue(resultSeat.getUser().getId() <= numberOfUsers);  // 예시 검증 로직

        log.info("Reserved by user ID: " + resultSeat.getUser().getId());
    }
}