package io.hhplus.concert.domain.service;

import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.domain.command.ReservationCommand;
import io.hhplus.concert.domain.entity.*;
import io.hhplus.concert.domain.respository.*;
import io.hhplus.concert.presentation.dto.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ConcertScheduleRepository concertScheduleRepository;
    private final ConcertSeatRepository concertSeatRepository;
    private final ConcertRepository concertRepository;
    private final ConcertReservationRepository concertReservationRepository;
    private final UserRepository userRepository;

    public ReservationDto.AvailableDateResponse availableDate(Long concertScheduleId) {
        ConcertSchedule concertSchedule = concertScheduleRepository.findById(concertScheduleId);
        return new ReservationDto.AvailableDateResponse(concertSchedule.getConcertScheduleId()
                                                ,concertSchedule.getConcertAt());
    }

    public List<ReservationDto.AvailableSeatResponse> availableSeat(Long concertScheduleId) {
        List<ConcertSeat> findConcertSeats = concertSeatRepository
                .findAllByConcertScheduleIdAndStatus(concertScheduleId, SeatStatus.UNASSIGNED);

        return findConcertSeats.stream()
                .map(s-> new ReservationDto.AvailableSeatResponse(concertScheduleId
                        ,s.getId()
                        ,s.getSeat())).toList();
    }

    public ReservationCommand.Reserved reservation(ReservationCommand.Reservation reservation){
        Concert concert = concertRepository.findById(reservation.concertId());
        ConcertSchedule concertSchedule = concertScheduleRepository.findById(reservation.concertScheduleId());
        User user = userRepository.findById(reservation.userId());
        if(ObjectUtils.isEmpty(concert)
           || ObjectUtils.isEmpty(concertSchedule)
           ||ObjectUtils.isEmpty(user)){
                throw new RuntimeException("잘못된 요청 입니다.");
        }

        LocalDateTime now = LocalDateTime.now();
        ConcertReservation concertReservation = createConcertReservation(concertSchedule, concert, user, now);
        ConcertReservation save = concertReservationRepository.save(concertReservation);

        ConcertSeat concertSeat =  concertSeatRepository.findById(reservation.concertSeatId());
        concertSeat.seatStatusTemp(concertSchedule, now);

        return ReservationCommand.of(save);
    }

    private ConcertReservation createConcertReservation(ConcertSchedule concertSchedule, Concert concert, User user, LocalDateTime now) {
        return ConcertReservation.reserveStatusTemp(concertSchedule, concert, user, concert.getConcertTitle(),
                concert.getDescription(), concertSchedule.getConcertAt(), concertSchedule.getPrice(), now);
    }

}
