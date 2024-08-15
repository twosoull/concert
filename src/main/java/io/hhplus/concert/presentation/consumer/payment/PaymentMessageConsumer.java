package io.hhplus.concert.presentation.consumer.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.concert.domain.message.PaymentMessage;
import io.hhplus.concert.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMessageConsumer {

    private final ReservationService reservationService;

    @KafkaListener(topics = "${payment.topic-name}", groupId = "group_1")
    public void listener(String message) throws JsonProcessingException {
        PaymentMessage paymentMessage = new ObjectMapper().readValue(message, PaymentMessage.class);
        System.out.println("데이터 왔다");
        System.out.println(paymentMessage.toString());

        reservationService.reserved(paymentMessage.getConcertReservationId(),
                paymentMessage.getConcertSeatId(),paymentMessage.convertReservationTime());
    }

}
