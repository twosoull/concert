package io.hhplus.concert.presentation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DataPlatformMockApiController {

    @PostMapping("/sendOrder")
    public String sendOrder() {
        // 간단한 Mock 응답
        return "Order information sent successfully";
    }
}