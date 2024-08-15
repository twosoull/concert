package io.hhplus.concert.domain.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.concert.common.enums.OutBoxStatus;
import io.hhplus.concert.domain.message.PaymentMessage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Setter
@Getter
public class PaymentOutBox {

    @Id @GeneratedValue
    @Column(name = "con_reservation_outbox_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private OutBoxStatus status;

    private String messageKey;
    private String messageValue;

    private LocalDateTime create_at;

    public PaymentOutBox() {
    }

    public PaymentOutBox(OutBoxStatus status, String messageKey, String messageValue) {
        this.status = status;
        this.messageKey = messageKey;
        this.messageValue = messageValue;
        this.create_at = LocalDateTime.now();
    }

    public static PaymentOutBox createOutBox(PaymentMessage message){

        ObjectMapper objectMapper = new ObjectMapper();

        String stringReservationInfo = null;
        try {
            stringReservationInfo = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new PaymentOutBox(OutBoxStatus.INIT,String.valueOf(message.getConcertReservationId()),stringReservationInfo);
    }
}
