package io.hhplus.concert.domain.respository;

import io.hhplus.concert.common.enums.OutBoxStatus;
import io.hhplus.concert.domain.entity.PaymentOutBox;

import java.util.List;

public interface PaymentOutboxRepository {
    PaymentOutBox findById(Long id);

    PaymentOutBox save(PaymentOutBox concertReservationOutBox);

    List<PaymentOutBox> findByStatus(OutBoxStatus outBoxStatus);
}
