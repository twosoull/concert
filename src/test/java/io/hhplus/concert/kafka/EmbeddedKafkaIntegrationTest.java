package io.hhplus.concert.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.concert.domain.message.PaymentMessage;
import io.hhplus.concert.infrastructure.kafka.payment.PaymentKafkaMessageSender;
import io.hhplus.concert.presentation.consumer.payment.PaymentMessageConsumer;
import io.hhplus.concert.presentation.consumer.payment.outbox.PaymentMessageOutBoxConsumer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9093", "port=9093" })
public class EmbeddedKafkaIntegrationTest {

    @Autowired
    PaymentKafkaMessageSender paymentKafkaMessageSender;


    @Autowired
    PaymentMessageOutBoxConsumer paymentMessageConsumer;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void test01() throws InterruptedException {
        // given
        Long outboxId = 21L;
        Long reservationId = 1L;
        Long concertScheduleId = 1L;
        Long seatIds = 1L;

        PaymentMessage message = PaymentMessage.getMessage(reservationId
                , concertScheduleId
                , LocalDateTime.now()
                , outboxId);
        // when
        paymentKafkaMessageSender.send(message);

        // then
        paymentMessageConsumer.getLatch().await(5, TimeUnit.SECONDS);
        Assertions.assertThat(paymentMessageConsumer.getPayload().getOutboxId()).isEqualTo(outboxId);
    }

}