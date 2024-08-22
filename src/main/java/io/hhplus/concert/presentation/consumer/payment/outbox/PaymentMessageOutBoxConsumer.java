package io.hhplus.concert.presentation.consumer.payment.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.concert.common.enums.OutBoxStatus;
import io.hhplus.concert.domain.entity.PaymentOutBox;
import io.hhplus.concert.domain.message.PaymentMessage;
import io.hhplus.concert.domain.respository.PaymentOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentMessageOutBoxConsumer {

    private final PaymentOutboxRepository paymentOutboxRepository;

    @KafkaListener(topics = "${payment.topic-name}", groupId ="reservation_outBox")
    @Transactional
    public void listener(String message) throws JsonProcessingException {
        log.info("데이터 아웃박스!!!!!");
        PaymentMessage paymentMessage = new ObjectMapper().readValue(message, PaymentMessage.class);

        PaymentOutBox findOutBox = paymentOutboxRepository.findById(paymentMessage.getOutboxId());
        findOutBox.setStatus(OutBoxStatus.PUBLISH);

    }
}
