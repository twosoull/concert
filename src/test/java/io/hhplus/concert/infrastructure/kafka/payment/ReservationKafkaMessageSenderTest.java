package io.hhplus.concert.infrastructure.kafka.payment;

import io.hhplus.concert.ConcertApplication;
import io.hhplus.concert.domain.message.PaymentMessage;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ConcertApplication.class)
class ReservationKafkaMessageSenderTest {

    @Autowired
    private PaymentKafkaMessageSender reservationKafkaMessageSender;

    @Test
    void test(){
        PaymentMessage paymentMessage = new PaymentMessage();
        //paymentMessage.setMessage("바보양!");
        //reservationKafkaMessageSender.send(paymentMessage);
    }

}