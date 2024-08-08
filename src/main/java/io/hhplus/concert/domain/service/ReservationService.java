package io.hhplus.concert.domain.service;

import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.common.utils.RequestTokenUtil;
import io.hhplus.concert.domain.command.ReservationCommand;
import io.hhplus.concert.domain.command.TokenCommand;
import io.hhplus.concert.domain.entity.*;
import io.hhplus.concert.domain.handler.exception.RestApiException;
import io.hhplus.concert.domain.handler.exception.TokenException;
import io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode;
import io.hhplus.concert.domain.respository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode.RESOURCE_NOT_FOUND;
import static io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode.SEAT_ALREADY_RESERVED;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {
    private final EntityManager entityManager;
    private final ConcertScheduleRepository concertScheduleRepository;
    private final ConcertSeatRepository concertSeatRepository;
    private final ConcertRepository concertRepository;
    private final ConcertReservationRepository concertReservationRepository;
    private final UserRepository userRepository;
    private final RequestTokenUtil requestTokenUtil;
    private final RedisRepository redisRepository;
    public ConcertSchedule getAvailableDate(Long concertScheduleId) {
        validToken();
        ConcertSchedule concertSchedule = concertScheduleRepository.findById(concertScheduleId);
        return concertSchedule;
    }

    public List<ConcertSeat> getAvailableSeat(Long concertScheduleId) {
        validToken();
        List<ConcertSeat> findConcertSeats = concertSeatRepository
                .findAllByConcertScheduleIdAndStatus(concertScheduleId, SeatStatus.UNASSIGNED);
        return findConcertSeats;
    }
    @Transactional
    public ConcertReservation reserve(ReservationCommand.reserve reserve){
        validToken();
        Long currentTokenUserId = requestTokenUtil.getCurrentTokenUserId();
        User user = userRepository.findById(currentTokenUserId);
        ConcertSchedule concertSchedule = concertScheduleRepository.findById(reserve.concertScheduleId());

        LocalDateTime now = LocalDateTime.now();

        if(reserve.LockType().equals("OPTIMISTIC"))
            return SeatTempReservationWithOptimisticLock(reserve.concertSeatId(), user, concertSchedule, now);

        if(reserve.LockType().equals("PESSIMISTIC"))
            return SeatTempReservationWithPessimisticLock(reserve.concertSeatId(), user, concertSchedule, now);

        return SeatTempReservation(reserve, user, concertSchedule, now);

    }

    private ConcertReservation SeatTempReservationWithOptimisticLock(Long concertSeatId, User user, ConcertSchedule concertSchedule, LocalDateTime now) {
        try {
            // 낙관적 락 예외가 발생할 수 있는 부분
            ConcertSeat concertSeat = concertSeatRepository.findByIdWithOptimisticLock(concertSeatId);
            concertSeat.setSeatStatusTemp(concertSchedule, user, now);

            // 즉시 플러시
            entityManager.flush();

            // 이후 로직
            ConcertReservation concertReservation = ConcertReservation.createReserveStatusTemp(concertSchedule, user, now);
            return concertReservationRepository.save(concertReservation);
        } catch (ObjectOptimisticLockingFailureException e) {
            // 예외 메시지와 스택 트레이스를 로그에 기록
            log.error("Optimistic locking failure for user: {}", user.getId(), e);
            throw new RestApiException(SEAT_ALREADY_RESERVED);
        }
    }


    private ConcertReservation SeatTempReservationWithPessimisticLock(Long concertSeatId, User user, ConcertSchedule concertSchedule, LocalDateTime now) {
        ConcertSeat concertSeat =  concertSeatRepository.findByIdWithPessimisticLock(concertSeatId);
        if(!concertSeat.getStatus().equals(SeatStatus.UNASSIGNED)){
            log.error("이미 예약된 좌석입니다. userId: {} ", user.getId());
            throw new RestApiException(SEAT_ALREADY_RESERVED);
        }
        log.error(" 저장 통과 userId: {} ", user.getId());
        concertSeat.setSeatStatusTemp(concertSchedule, user, now);

        ConcertReservation concertReservation = ConcertReservation.createReserveStatusTemp(concertSchedule, user, now);
        return concertReservationRepository.save(concertReservation);

    }

    private ConcertReservation SeatTempReservation(ReservationCommand.reserve reserve, User user, ConcertSchedule concertSchedule, LocalDateTime now) {
        ConcertSeat concertSeat =  concertSeatRepository.findById(reserve.concertSeatId());
        if(!concertSeat.getStatus().equals(SeatStatus.UNASSIGNED)){
            log.error("이미 예약된 좌석입니다. userId: {} ", user.getId());
            throw new RestApiException(SEAT_ALREADY_RESERVED);
        }
        log.error(" 저장 통과 userId: {} ", user.getId());
        concertSeat.setSeatStatusTemp(concertSchedule, user, now);

        ConcertReservation concertReservation = ConcertReservation.createReserveStatusTemp(concertSchedule, user, now);
        return concertReservationRepository.save(concertReservation);

    }

    private void validToken() {
        String currentToken = requestTokenUtil.getCurrentToken();
        boolean isActive = redisRepository.isActiveMember(currentToken);

        //액티브에 없으면
        if (!isActive) {
            throw new TokenException(RESOURCE_NOT_FOUND);
        }
    }
}
