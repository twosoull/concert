package io.hhplus.concert.domain.message;

public interface PaymentMessageSender {

    void send(PaymentMessage message);

}
