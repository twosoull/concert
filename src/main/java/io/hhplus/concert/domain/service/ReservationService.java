package io.hhplus.concert.domain.service;

import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.common.utils.RequestTokenUtil;
import io.hhplus.concert.domain.command.ReservationCommand;
import io.hhplus.concert.domain.entity.*;
import io.hhplus.concert.domain.handler.exception.RestApiException;
import io.hhplus.concert.domain.respository.*;
import io.hhplus.concert.presentation.holder.RequestTokenHolder;
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
    private final RequestTokenUtil requestTokenUtil;

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
        Long currentTokenUserId = requestTokenUtil.getCurrentTokenUserId();
        Concert concert = concertRepository.findById(reserve.concertId());
        ConcertSchedule concertSchedule = concertScheduleRepository.findById(reserve.concertScheduleId());
        User user = userRepository.findById(currentTokenUserId);

        if(concert == null || concertSchedule == null || user == null){
                throw new RestApiException(RESOURCE_NOT_FOUND);
        }

        LocalDateTime now = LocalDateTime.now();
        ConcertReservation concertReservation = ConcertReservation.createReserveStatusTemp(concertSchedule,user,now);
        ConcertReservation saveConcertReservation = concertReservationRepository.save(concertReservation);

        ConcertSeat concertSeat =  concertSeatRepository.findById(reserve.concertSeatId());
        concertSeat.setSeatStatusTemp(concertSchedule, now);

        return saveConcertReservation;
    }
}
