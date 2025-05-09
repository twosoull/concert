package io.hhplus.concert.presentation.controller;

import io.hhplus.concert.domain.service.WalletService;
import io.hhplus.concert.presentation.dto.WalletDto;
import io.hhplus.concert.presentation.mockApi.dto.WalletBalanceResDto;
import io.hhplus.concert.presentation.mockApi.dto.WalletChargeReqDto;
import io.hhplus.concert.presentation.mockApi.dto.WalletChargeResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/wallet/balance/info")
    public ResponseEntity<WalletDto.BalanceInfoResponse> getBalanceInfo(@RequestBody WalletDto.BalanceInfoRequest balanceInfoRequest) {
        return new ResponseEntity<>(
                WalletDto.balanceInfoResponseOf(
                        walletService.getBalanceInfo()),
                HttpStatus.OK);
    }

    @PostMapping("/wallet/charge")
    public ResponseEntity<WalletDto.ChargeResponse> chargeWallet(@RequestBody WalletDto.ChargeRequest chargeRequest) {

        return new ResponseEntity<>(
                WalletDto.ChargeResponseOf(walletService.chargeWallet(
                        WalletDto.chargeToCommand(chargeRequest))),
                HttpStatus.OK);
    }
}
