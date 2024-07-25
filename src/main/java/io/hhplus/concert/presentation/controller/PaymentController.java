package io.hhplus.concert.presentation.controller;

import io.hhplus.concert.domain.service.PaymentService;
import io.hhplus.concert.presentation.dto.PaymentDto;
import io.hhplus.concert.presentation.mockApi.dto.PaymentResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static io.hhplus.concert.presentation.dto.PaymentDto.paymentReqToCommend;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity<PaymentDto.PaymentResponse> pay(@RequestBody PaymentDto.PaymentRequest paymentRequest) {
        return new ResponseEntity<>(
                PaymentDto.paymentResponseOf(paymentService.pay(
                        paymentReqToCommend(paymentRequest)
                ))
        , HttpStatus.OK);
    }
}
