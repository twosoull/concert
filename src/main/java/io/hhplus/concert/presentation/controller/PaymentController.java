package io.hhplus.concert.presentation.controller;

import io.hhplus.concert.presentation.mockApi.dto.PaymentResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class PaymentController {

    @PostMapping("/payment")
    public ResponseEntity<PaymentResDto> payment(@RequestBody Long userId) {
        return new ResponseEntity<>(new PaymentResDto(1000L,2000L,"향해하기 좋은 남자", LocalDateTime.of(2024, 6, 23, 14, 30) , 1)
        , HttpStatus.OK);

    }
}
