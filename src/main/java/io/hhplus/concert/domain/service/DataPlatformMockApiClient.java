package io.hhplus.concert.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class DataPlatformMockApiClient {

    private final RestTemplate restTemplate;

    public void sendOrder() {
        String url = "http://localhost:8080/api/sendOrder";
        restTemplate.postForObject(url, null, String.class);
    }
}