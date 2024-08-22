package io.hhplus.concert;

import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.entity.*;
import io.hhplus.concert.infrastructure.repositoryImpl.*;
import io.hhplus.concert.infrastructure.repositoryORM.TokenJpaRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Transactional
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepositoryImpl userRepository;

    private final TokenRepositoryImpl tokenRepository;

    private final ConcertRepositoryImpl concertRepository;

    private final ConcertScheduleRepositoryImpl concertScheduleRepository;

    private final ConcertSeatRepositoryImpl concertSeatRepository;

    private final WalletRepositoryImpl walletRepository;

    private final ConcertReservationRepositoryImpl concertReservationRepository;
    private final  EntityManager em;

    @Bean
    public CommandLineRunner initData() {


        return args -> {

            for(Long i = 1L; i < 20000L; i++){
                User user = new User(i,"dsa","fasd");
                userRepository.save(user);
                Token token = null;
                if( i <27L){
                    //token = Token.create(user,TokenStatus.ACTIVE);
                }else{
                    //token = Token.create(user,TokenStatus.WAIT);
                }
                //tokenRepository.save(token);
                Wallet wallet = new Wallet(user, 100000L);
                walletRepository.save(wallet);
            }
/*
            for(Long i = 100L; i < 120L; i++) {
                User user = new User(i, "dsa", "fasd");
                userRepository.save(user);
            }
*/
            User user = userRepository.findById(1L);
            for (Long i = 1L; i <= 1L; i++) {
                Concert concert = new Concert(i, "향해하는 남자", "욕심은 버려라, 할 수 있는 만큼만 해보자");
                concertRepository.save(concert);

                Long concertScheduleId = (i - 1) * 2L + 1L;
                for (Long j = 0L; j < 2L; j++) {
                    ConcertSchedule concertSchedule = new ConcertSchedule(concertScheduleId + j, concert, LocalDateTime.now(), 100L, 10000L, LocalDateTime.now(), LocalDateTime.now(),concertScheduleId);
                    concertScheduleRepository.save(concertSchedule);

                    Long concertSeatIdStart = (i - 1) * 3L + j * 2000L + 1L;  // 수정된 계산 방식
                    ConcertReservation concertReservation = ConcertReservation.createReserveStatusTemp(concertSchedule,  user, LocalDateTime.now());
                    concertReservationRepository.save(concertReservation);
                    List<ConcertSeat> concertSeats = new ArrayList<>();
                    for (Long s = 0L; s < 2000L; s++) {
                        ConcertSeat concertSeat = new ConcertSeat(concertSeatIdStart + s, concertSchedule, null, 1L, SeatStatus.UNASSIGNED, null, null,concertSeatIdStart + s);
                        concertSeats.add(concertSeat);

                        concertSeatRepository.save(concertSeat);
                    }
                }
            }

        };
    }
}