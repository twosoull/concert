package io.hhplus.concert.infrastructure.repositoryImpl;

import io.hhplus.concert.common.enums.OutBoxStatus;
import io.hhplus.concert.domain.entity.PaymentOutBox;
import io.hhplus.concert.domain.handler.exception.RestApiException;
import io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode;
import io.hhplus.concert.domain.respository.PaymentOutboxRepository;
import io.hhplus.concert.infrastructure.repositoryORM.PaymentOutboxJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentOutboxRepositoryImpl implements PaymentOutboxRepository {

    private final PaymentOutboxJpaRepository paymentOutboxJpaRepository;

    @Override
    public PaymentOutBox findById(Long id) {
        return paymentOutboxJpaRepository.findById(id).orElseThrow(
                () -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND)
        );
    }

    @Override
    public PaymentOutBox save(PaymentOutBox paymentOutBox) {
        return paymentOutboxJpaRepository.save(paymentOutBox);
    }

    @Override
    public List<PaymentOutBox> findByStatus(OutBoxStatus outBoxStatus) {
        return paymentOutboxJpaRepository.findByStatus(outBoxStatus);
    }
}
