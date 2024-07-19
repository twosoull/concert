package io.hhplus.concert.presentation.controller;

import io.hhplus.concert.domain.service.TokenService;
import io.hhplus.concert.presentation.dto.TokenDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/api/token/create")
    public ResponseEntity<TokenDto.createTokenResponse> createToken(@RequestBody TokenDto.createTokenRequest tokenReqDto
                                                            ,HttpServletResponse response) {
        TokenDto.createTokenResponse createTokenResponse = TokenDto.createResponse(tokenService.createToken(tokenReqDto.userId()));
        response.setHeader("Authorization", "Bearer " + createTokenResponse.token());

        return new ResponseEntity<>(createTokenResponse, HttpStatus.OK);
    }


    @GetMapping("/api/token/info")
    public ResponseEntity<TokenDto.infoTokenResponse> getTokeninfo(
                                                    //@RequestBody TokenDto.infoTokenRequest tokenReqDto,
                                                    HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);

        return new ResponseEntity<>(
                TokenDto.infoResponse(tokenService.getTokenInfo(token))
                ,HttpStatus.OK);
    }

    //@PostMapping("/api/token/update")
    public void update() {
        //스케줄러 이용
    }
}
