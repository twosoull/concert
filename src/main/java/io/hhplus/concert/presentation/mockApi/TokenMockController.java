package io.hhplus.concert.presentation.mockApi;

import io.hhplus.concert.presentation.mockApi.dto.TokenResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenMockController {

    @PostMapping("/token/create")
    public ResponseEntity<TokenResDto> create() {
        return new ResponseEntity<TokenResDto>(new TokenResDto(20), HttpStatus.OK);
    }

    @GetMapping("/token/info")
    public ResponseEntity<TokenResDto> info() {
        return new ResponseEntity<TokenResDto>(new TokenResDto(20), HttpStatus.OK);
    }

    @PostMapping("/token/update")
    public void update() {
        //스케줄러 이용
    }
}
