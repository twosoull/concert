package io.hhplus.concert.presentation.controller;

import io.hhplus.concert.presentation.mockApi.dto.WalletBalanceResDto;
import io.hhplus.concert.presentation.mockApi.dto.WalletChargeReqDto;
import io.hhplus.concert.presentation.mockApi.dto.WalletChargeResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class WalletController {

    //@GetMapping("/wallet/balance/info")
    public ResponseEntity<WalletBalanceResDto> balanceInfo(@RequestBody Long userId) {

        return new ResponseEntity<>(new WalletBalanceResDto(100000L), HttpStatus.OK);
    }

    //@PostMapping("/wallet/charge")
    public ResponseEntity<WalletChargeResDto> charge(@RequestBody WalletChargeReqDto walletChargeReqDto) {

        return new ResponseEntity<>(new WalletChargeResDto(2000L, 3000L,5000L, 5000L, LocalDateTime.of(2024, 6, 23, 14, 30)),
                HttpStatus.OK);
    }
}
