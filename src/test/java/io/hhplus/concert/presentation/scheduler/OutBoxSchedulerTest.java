package io.hhplus.concert.presentation.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.hhplus.concert.ConcertApplication;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConcertApplication.class)
@RequiredArgsConstructor
public class OutBoxSchedulerTest {

    @Autowired
    OutBoxScheduler outBoxScheduler;

    @Test
    public void OutBoxScheduler_test() throws JsonProcessingException {
        outBoxScheduler.setPaymentOutBoxPublish();
    }

}