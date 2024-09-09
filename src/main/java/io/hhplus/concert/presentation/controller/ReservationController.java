package io.hhplus.concert.presentation.controller;

import io.hhplus.concert.domain.service.ReservationService;
import io.hhplus.concert.presentation.dto.ReservationDto;
import io.hhplus.concert.presentation.mockApi.dto.ReservationDateResDto;
import io.hhplus.concert.presentation.mockApi.dto.ReservationResDto;
import io.hhplus.concert.presentation.mockApi.dto.ReservationSeatResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/reservation/available/date")
    public ResponseEntity<ReservationDto.AvailableDateResponse> getAvailableDate(@RequestBody Long concertScheduleId) {

        return new ResponseEntity<>
                (ReservationDto.ResponseDateDto(reservationService.getAvailableDate(concertScheduleId))
                        ,HttpStatus.OK);
    }

    @GetMapping("/reservation/available/seat")
    public ResponseEntity<List<ReservationDto.AvailableSeatResponse>> getAvailableSeat(@RequestBody Long concertScheduleId) {
        return new ResponseEntity<>(
                reservationService.getAvailableSeat(concertScheduleId).stream().map(
                        s -> ReservationDto.ResponseSeatDto(s)).toList()
                ,HttpStatus.OK);
    }

    @PostMapping("/reservation")
    public ResponseEntity<ReservationDto.ReservationResponse> reserve(@RequestBody ReservationDto.ReservationRequest reservationRequest) {

        return new ResponseEntity<>(
                ReservationDto.ResponseResevationDto(reservationService.reserve(
                        ReservationDto.toCommand(reservationRequest)))
                ,HttpStatus.OK);
    }
}
