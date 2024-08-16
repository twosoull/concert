package io.hhplus.concert.testContainer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.concert.common.enums.OutBoxStatus;
import io.hhplus.concert.domain.entity.PaymentOutBox;
import io.hhplus.concert.domain.message.PaymentMessage;
import io.hhplus.concert.domain.respository.PaymentOutboxRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.assertj.core.api.Assertions.assertThat;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@TestPropertySource(
        properties = {
                "spring.kafka.consumer.auto-offset-reset=earliest",
                "spring.kafka.consumer.enable.auto.commit=false",
                "spring.datasource.url=jdbc:h2:tcp://localhost/~/concert",
        }
)
@Testcontainers
public class PaymentEventTestContainer {

    @Container
    static final KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.6.1")
    );

    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private PaymentOutboxRepository paymentOutboxRepository;

    private Long outBoxId = 1L;

    @BeforeEach
    void setUp() {
        PaymentOutBox product = new PaymentOutBox(outBoxId,OutBoxStatus.INIT, null, "{concertResevationId : \"1\", concertSeatId : \"1\" }");
        paymentOutboxRepository.save(product);
    }

    @Test
    @DisplayName("kafka로 보낸 메세지를 아웃박스 컨슘이 읽어들여 상태를 업데이트한다.")
    void kafkaPaymentEventOutBoxConsum_test() throws JsonProcessingException {
        Long concertReservationId = 1L;
        Long concertSeatId = 1L;
        String parsedLocalDateTimeNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        PaymentMessage message = new PaymentMessage(
                concertReservationId, concertSeatId, parsedLocalDateTimeNow,outBoxId
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String stringReservationInfo = objectMapper.writeValueAsString(message);

        kafkaTemplate.send("testTopic", String.valueOf(concertReservationId), stringReservationInfo);

        await()
                .pollInterval(Duration.ofSeconds(3))
                .atMost(10, SECONDS)
                .untilAsserted(() -> {
                    PaymentOutBox optionalProduct = paymentOutboxRepository.findById(
                            outBoxId
                    );

                    Thread.sleep(3000);
                    assertThat(optionalProduct.getId()).isEqualTo(outBoxId);
                    assertThat(optionalProduct.getStatus()).isEqualTo(OutBoxStatus.PUBLISH);
                });
    }

}
