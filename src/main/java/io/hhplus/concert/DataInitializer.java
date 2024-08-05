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
    private final  EntityManager em;

    @Bean
    public CommandLineRunner initData() {
        return args -> {

            for(Long i = 1L; i < 100L; i++){
                User user = new User(i,"dsa","fasd");
                userRepository.save(user);
                Token token = null;
                if( i <27L){
                    //token = Token.create(user,TokenStatus.ACTIVE);
                }else{
                    //token = Token.create(user,TokenStatus.WAIT);
                }
                //tokenRepository.save(token);
            }

            for(Long i = 100L; i < 120L; i++) {
                User user = new User(i, "dsa", "fasd");
                userRepository.save(user);
            }

            Concert concert = new Concert(1L, "향해하는 남자", "욕심은 버려라, 할 수 있는 만큼만 해보자");
            concertRepository.save(concert);

            ConcertSchedule concertSchedule = new ConcertSchedule(concert, LocalDateTime.now(),100L
                    ,10000L,LocalDateTime.now(),LocalDateTime.now());

            concertScheduleRepository.save(concertSchedule);

            ConcertSeat concertSeat = new ConcertSeat(concertSchedule,
                    null, 1L, SeatStatus.UNASSIGNED, null,null);

            concertSeatRepository.save(concertSeat);

            User user = userRepository.findById(1L);

            Wallet wallet = new Wallet(user, 100000L);
            walletRepository.save(wallet);

        };
    }
}