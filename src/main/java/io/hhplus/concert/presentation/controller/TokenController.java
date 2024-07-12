package io.hhplus.concert.presentation.controller;

import io.hhplus.concert.domain.command.TokenCommand;
import io.hhplus.concert.domain.entity.Token;
import io.hhplus.concert.domain.service.TokenService;
import io.hhplus.concert.presentation.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    //@PostMapping("/api/token/create")
    public ResponseEntity<TokenCommand.TokenCreateResDto> create(@RequestBody TokenDto.createTokenRequest tokenReqDto) {
        return new ResponseEntity<>(tokenService.create(tokenReqDto.userId()),HttpStatus.OK);
    }

    /*
    @GetMapping("/api/token/info")
    public ResponseEntity<Token> info(@RequestBody TokenDto.infoTokenRequest tokenReqDto) {
        return new ResponseEntity<>(tokenService.info(tokenReqDto.userId()),HttpStatus.OK);
    }
*/
    //@PostMapping("/api/token/update")
    public void update() {
        //스케줄러 이용
    }
}
