package io.hhplus.concert.infrastructure.repositoryORM;

import io.hhplus.concert.common.enums.OutBoxStatus;
import io.hhplus.concert.domain.entity.PaymentOutBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentOutboxJpaRepository extends JpaRepository<PaymentOutBox, Long> {
    List<PaymentOutBox> findByStatus(OutBoxStatus outBoxStatus);
}
