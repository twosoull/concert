package io.hhplus.concert.presentation.mockApi;

import io.hhplus.concert.presentation.mockApi.dto.ReservationDateResDto;
import io.hhplus.concert.presentation.mockApi.dto.ReservationResDto;
import io.hhplus.concert.presentation.mockApi.dto.ReservationSeatResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ReservationMockController {

    //@GetMapping("/reservation/available/date")
    public ResponseEntity<List<ReservationDateResDto>> availableDate(@RequestBody Long concertScheduleId) {

        return new ResponseEntity<>(List.of(new ReservationDateResDto(1L,1L, LocalDateTime.of(2024, 6, 23, 14, 30),20,50,5000),
                new ReservationDateResDto(2L,1L, LocalDateTime.of(2024, 6, 23, 14, 30),20,50,5000)), HttpStatus.OK);
    }

    //@GetMapping("/reservation/available/seat")
    public ResponseEntity<List<ReservationSeatResDto>> availableSeat() {

        return new ResponseEntity<>(List.of(new ReservationSeatResDto(1L,1L,1,"",null),
                new ReservationSeatResDto(1L,1L,1,"temp",LocalDateTime.of(2024, 6, 23, 14, 30))),HttpStatus.OK);
    }

    //@PostMapping("/reservation")
    public ResponseEntity<ReservationResDto> reservation() {
        return new ResponseEntity<>(new ReservationResDto(1L,"향해하고 싶은 남자","향해를 꿈꾸던 99번 남자는 배에 오르게 되는데..",
                LocalDateTime.of(2024, 6, 23, 14, 30),1,2000L,LocalDateTime.of(2024, 6, 23, 14, 30))
        ,HttpStatus.OK);
    }
}
