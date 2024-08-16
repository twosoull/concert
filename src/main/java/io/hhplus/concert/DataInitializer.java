package io.hhplus.concert;

import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.entity.Concert;
import io.hhplus.concert.domain.entity.ConcertSchedule;
import io.hhplus.concert.domain.entity.Token;
import io.hhplus.concert.domain.entity.User;
import io.hhplus.concert.infrastructure.repositoryImpl.ConcertRepositoryImpl;
import io.hhplus.concert.infrastructure.repositoryImpl.ConcertScheduleRepositoryImpl;
import io.hhplus.concert.infrastructure.repositoryImpl.TokenRepositoryImpl;
import io.hhplus.concert.infrastructure.repositoryImpl.UserRepositoryImpl;
import io.hhplus.concert.infrastructure.repositoryORM.TokenJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Configuration
@Transactional
public class DataInitializer {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private TokenRepositoryImpl tokenRepository;

    @Autowired
    private ConcertRepositoryImpl concertRepository;

    @Autowired
    private ConcertScheduleRepositoryImpl concertScheduleRepository;

    @Autowired
    private EntityManager em;
    @Bean
    public CommandLineRunner initData() {
        return args -> {

            for(Long i = 1L; i < 100L; i++){
                User user = new User(i,"dsa","fasd");
                userRepository.save(user);
                Token token = null;
                if( i <30L){
                    token = Token.create(user,TokenStatus.ACTIVE);
                }
                token = Token.create(user,TokenStatus.WAIT);
                tokenRepository.save(token);
            }

            Concert concert = new Concert(1L, "향해하는 남자", "욕심은 버려라, 할 수 있는 만큼만 해보자");
            concertRepository.save(concert);

            ConcertSchedule concertSchedule = new ConcertSchedule(concert, LocalDateTime.now(),100L
                    ,10000L,LocalDateTime.now(),LocalDateTime.now());

            concertScheduleRepository.save(concertSchedule);
        };
    }
}