package io.hhplus.concert.presentation.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.hhplus.concert.ConcertApplication;
import io.hhplus.concert.common.enums.OutBoxStatus;
import io.hhplus.concert.domain.entity.PaymentOutBox;
import io.hhplus.concert.domain.respository.PaymentOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConcertApplication.class)
@RequiredArgsConstructor
public class OutBoxSchedulerTest {

    @Autowired
    OutBoxScheduler outBoxScheduler;

    @Autowired
    PaymentOutboxRepository paymentOutboxRepository;

    @Test
    public void OutBoxScheduler_test() throws JsonProcessingException {
        // given
        int initMessages = 4;
        List<PaymentOutBox> outboxes = paymentOutboxRepository.findByStatus(OutBoxStatus.INIT);
        assertThat(outboxes.size()).isEqualTo(initMessages);

        // when
        outBoxScheduler.setPaymentOutBoxPublish();

        // then
        Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    int publishedMessages = 4;
                    List<PaymentOutBox> changedOutboxes = paymentOutboxRepository.findByStatus(OutBoxStatus.PUBLISH);
                    assertThat(changedOutboxes.size()).isEqualTo(publishedMessages);
        });
    }

}