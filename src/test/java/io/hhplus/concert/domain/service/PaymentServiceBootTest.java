package io.hhplus.concert.domain.service;

import io.hhplus.concert.ConcertApplication;
import io.hhplus.concert.common.utils.RequestTokenUtil;
import io.hhplus.concert.domain.command.PaymentCommand;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConcertApplication.class)
@RequiredArgsConstructor
class PaymentServiceBootTest {

    @Autowired
    private PaymentService paymentService;

    @MockBean
    private RequestTokenUtil requestTokenUtil;

    @Test
    void pay() throws InterruptedException {
        Long userId = 1L;
        Long concertScheduleId = 1L;
        Long concertReservationId= 1L;
        Long concertSeatId = 1L;

        doReturn(userId).when(requestTokenUtil).getCurrentTokenUserId();

        PaymentCommand.Pay pay = new PaymentCommand.Pay(concertScheduleId,concertReservationId,concertSeatId);
        paymentService.pay(pay);

        Thread.sleep(3000);
    }
}