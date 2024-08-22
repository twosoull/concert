package io.hhplus.concert.domain.event;

import io.hhplus.concert.domain.entity.PaymentOutBox;
import io.hhplus.concert.domain.message.PaymentMessage;
import io.hhplus.concert.domain.respository.PaymentOutboxRepository;
import io.hhplus.concert.infrastructure.kafka.payment.PaymentKafkaMessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventListener {

    private final PaymentKafkaMessageSender paymentKafkaMessageSender;

    private final PaymentOutboxRepository PaymentOutboxRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void paymentEvent(PaymentEvent event){
        paymentKafkaMessageSender.send(PaymentMessage.getMessage(event.getConcertReservationId()
                , event.getConcertSeatId()
                , event.getReservationTime()
                , event.getOutboxId()));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void outboxEvent(PaymentEvent event){
        String parsedLocalDateTimeNow = event.getReservationTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // 아웃박스
        PaymentMessage message = new PaymentMessage(event.getConcertReservationId(), event.getConcertSeatId(), parsedLocalDateTimeNow);

        PaymentOutBox saveOutbox = PaymentOutboxRepository.save(PaymentOutBox.createOutBox(message));
        event.setOutboxId(saveOutbox.getId());
    }

}
