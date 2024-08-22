package io.hhplus.concert.infrastructure.kafka.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.concert.domain.handler.exception.RestApiException;
import io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode;
import io.hhplus.concert.domain.message.PaymentMessage;
import io.hhplus.concert.domain.message.PaymentMessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentKafkaMessageSender implements PaymentMessageSender{

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${payment.topic-name}")
    private String PAYMENT_TOPIC;

    @Override
    public void send(PaymentMessage message) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String stringReservationInfo = objectMapper.writeValueAsString(message);
            kafkaTemplate.send(PAYMENT_TOPIC, String.valueOf(message.getConcertReservationId()), stringReservationInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RestApiException(CommonErrorCode.KAFKA_PRODUCER_FAIL);
        }

    }
}
