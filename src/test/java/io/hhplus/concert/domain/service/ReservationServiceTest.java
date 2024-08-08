package io.hhplus.concert.domain.service;

import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.domain.command.ReservationCommand;
import io.hhplus.concert.domain.entity.*;
import io.hhplus.concert.domain.respository.*;
import io.hhplus.concert.presentation.dto.ReservationDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Mock
    ConcertRepository concertRepository;

    @Mock
    ConcertScheduleRepository concertScheduleRepository;

    @Mock
    ConcertSeatRepository concertSeatRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ConcertReservationRepository concertReservationRepository;
    @Test
    @Disabled("예약 가능 날짜 조회 테스트")
    void availableDate_test() {
        //given
        Long concertScheduleId = 1L;
        Concert concert = new Concert(1L, "향해하는 남자", "욕심은 버려라, 할 수 있는 만큼만 해보자");
        ConcertSchedule givenConcertSchedule = new ConcertSchedule(concertScheduleId, concert, LocalDateTime.now(),100L
                ,10000L,LocalDateTime.now(),LocalDateTime.now());

        doReturn(givenConcertSchedule).when(concertScheduleRepository).findById(any(Long.class));

        //when
        ConcertSchedule concertSchedule = reservationService.getAvailableDate(concertScheduleId);

        //then
        assertEquals(concertScheduleId,concertSchedule.getId());
    }

    @Test
    @Disabled("예약 가능 자리 조회 테스트")
    void availableSeat_test() {
        //given
        Long concertScheduleId = 1L;
        User givenUser = new User(1L, "asd", "향해하는남자");
        Concert givenConcert = new Concert(1L, "향해하는 남자", "욕심은 버려라, 할 수 있는 만큼만 해보자");
        ConcertSchedule givenConcertSchedule = new ConcertSchedule(concertScheduleId, givenConcert, LocalDateTime.now(),100L
                ,10000L,LocalDateTime.now(),LocalDateTime.now());

        List<ConcertSeat> concertSeats = new ArrayList<>();
        for(Long i = 1L ; i < 51L; i++){
            ConcertSeat concertSeat = null;
            if(i < 21L){ // 20개 남은 좌석
                concertSeat = new ConcertSeat(i, givenConcertSchedule, givenUser, i, SeatStatus.UNASSIGNED, null, null);
                concertSeats.add(concertSeat);
            }
        }
        doReturn(concertSeats).when(concertSeatRepository).findAllByConcertScheduleIdAndStatus(any(Long.class),any(SeatStatus.class));

        //when
        List<ConcertSeat> findConcertSeats = reservationService.getAvailableSeat(concertScheduleId);

        //then
        assertEquals(20,findConcertSeats.size());
        assertEquals(concertScheduleId,findConcertSeats.get(0).getSeat());
        assertEquals(20L,findConcertSeats.get(findConcertSeats.size()-1).getSeat());
    }

    @Test
    @DisplayName("임시 예약 테스트")
    void reservation(){
        //given
        Long concertId = 1L;
        Long concertScheduleId = 1L;
        Long concertSeatId = 1L;
        Long userId = 1L;
        String concertTitle = "행복한 향해중";

        User givenUser = new User(1L, "asd", "향해하는남자");
        Concert givenConcert = new Concert(1L, concertTitle, "욕심은 버려라, 할 수 있는 만큼만 해보자");
        ConcertSchedule givenConcertSchedule = new ConcertSchedule(concertScheduleId, givenConcert, LocalDateTime.now(),100L
                ,10000L,LocalDateTime.now(),LocalDateTime.now());
        ConcertSeat givenConcertSeat = new ConcertSeat(concertSeatId,givenConcertSchedule,givenUser,1L,SeatStatus.TEMP,LocalDateTime.now(), null);

        ConcertReservation concertReservation = ConcertReservation.createReserveStatusTemp(givenConcertSchedule, givenUser, LocalDateTime.now());

        doReturn(givenConcert).when(concertRepository).findById(concertId);
        doReturn(givenConcertSchedule).when(concertScheduleRepository).findById(concertScheduleId);
        doReturn(givenUser).when(userRepository).findById(userId);
        doReturn(givenConcertSeat).when(concertSeatRepository).findById(concertSeatId);

        doReturn(concertReservation).when(concertReservationRepository).save(any(ConcertReservation.class));
        ReservationCommand.reserve reservation = new ReservationCommand.reserve(concertId, concertScheduleId, concertSeatId,"ß");

        //when
        ConcertReservation reserveConcertReservation = reservationService.reserve(reservation);

        assertEquals(reserveConcertReservation.getConcertTitle(), concertTitle);

    }

    @Test
    @DisplayName("임시 예약 테스트 - null 객체 테스트")
    void reservation_nullObject(){
        //given
        Long concertId = 1L;
        Long concertScheduleId = 1L;
        Long concertSeatId = 1L;
        Long userId = 1L;
        String concertTitle = "행복한 향해중";

        User givenUser = new User(1L, "asd", "향해하는남자");
        Concert givenConcert = new Concert(1L, concertTitle, "욕심은 버려라, 할 수 있는 만큼만 해보자");
        ConcertSchedule givenConcertSchedule = new ConcertSchedule(concertScheduleId, givenConcert, LocalDateTime.now(),100L
                ,10000L,LocalDateTime.now(),LocalDateTime.now());
        ConcertSeat givenConcertSeat = new ConcertSeat(concertSeatId,givenConcertSchedule,givenUser,1L,SeatStatus.TEMP,LocalDateTime.now(), null);

        ConcertReservation concertReservation = ConcertReservation.createReserveStatusTemp(givenConcertSchedule,  givenUser, LocalDateTime.now());

        doReturn(null).when(concertRepository).findById(concertId);

        ReservationCommand.reserve reservation = new ReservationCommand.reserve(concertId, concertScheduleId, concertSeatId,"");

        //when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservationService.reserve(reservation);
        });

        // 예외 메시지 검증
        assertEquals("잘못된 요청 입니다.", exception.getMessage());
    }
}