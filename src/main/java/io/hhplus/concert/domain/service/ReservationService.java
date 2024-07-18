package io.hhplus.concert.domain.service;

import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.domain.command.ReservationCommand;
import io.hhplus.concert.domain.entity.*;
import io.hhplus.concert.domain.handler.exception.RestApiException;
import io.hhplus.concert.domain.respository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode.RESOURCE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ConcertScheduleRepository concertScheduleRepository;
    private final ConcertSeatRepository concertSeatRepository;
    private final ConcertRepository concertRepository;
    private final ConcertReservationRepository concertReservationRepository;
    private final UserRepository userRepository;

    public ConcertSchedule getAvailableDate(Long concertScheduleId) {
        ConcertSchedule concertSchedule = concertScheduleRepository.findById(concertScheduleId);
        return concertSchedule;
    }

    public List<ConcertSeat> getAvailableSeat(Long concertScheduleId) {
        List<ConcertSeat> findConcertSeats = concertSeatRepository
                .findAllByConcertScheduleIdAndStatus(concertScheduleId, SeatStatus.UNASSIGNED);

        return findConcertSeats;
    }

    public ConcertReservation reserve(ReservationCommand.reserve reserve){
        Concert concert = concertRepository.findById(reserve.concertId());
        ConcertSchedule concertSchedule = concertScheduleRepository.findById(reserve.concertScheduleId());
        User user = userRepository.findById(reserve.userId());
        if(concert == null || concertSchedule == null || user == null){
                throw new RestApiException(RESOURCE_NOT_FOUND);
        }

        LocalDateTime now = LocalDateTime.now();
        ConcertReservation concertReservation = createConcertReservation(concertSchedule, concert, user, now);
        ConcertReservation saveConcertReservation = concertReservationRepository.save(concertReservation);

        ConcertSeat concertSeat =  concertSeatRepository.findById(reserve.concertSeatId());
        concertSeat.seatStatusTemp(concertSchedule, now);

        return saveConcertReservation;
    }

    private ConcertReservation createConcertReservation(ConcertSchedule concertSchedule, Concert concert, User user, LocalDateTime now) {
        return ConcertReservation.reserveStatusTemp(concertSchedule, concert, user, concert.getConcertTitle(),
                concert.getDescription(), concertSchedule.getConcertAt(), concertSchedule.getPrice(), now);
    }

}
