package io.hhplus.concert.presentation.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.concert.common.enums.OutBoxStatus;
import io.hhplus.concert.domain.entity.PaymentOutBox;
import io.hhplus.concert.domain.message.PaymentMessage;
import io.hhplus.concert.domain.respository.PaymentOutboxRepository;
import io.hhplus.concert.infrastructure.kafka.payment.PaymentKafkaMessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutBoxScheduler {

    private final PaymentOutboxRepository paymentOutboxRepository;

    private final PaymentKafkaMessageSender paymentKafkaMessageSender;

    @Scheduled(fixedRate = 5000) // 5초마다 실행
    public void setPaymentOutBoxPublish() throws JsonProcessingException {
        List<PaymentOutBox> paymentOutBoxes = paymentOutboxRepository.findByStatus(OutBoxStatus.INIT);
        log.info(paymentOutBoxes.toString());

        for(PaymentOutBox paymentOutBox : paymentOutBoxes){
            if(Duration.between(paymentOutBox.getCreate_at(), LocalDateTime.now()).toMinutes() >= 5){
                PaymentMessage paymentMessage = new ObjectMapper().readValue(paymentOutBox.getMessageValue(), PaymentMessage.class);
                paymentKafkaMessageSender.send(paymentMessage);
            }
        }
    }

}
