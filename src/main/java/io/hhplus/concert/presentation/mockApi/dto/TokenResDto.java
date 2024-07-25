package io.hhplus.concert.presentation.mockApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResDto {

    private int waiting;

    public TokenResDto(int waiting) {
        this.waiting = waiting;
    }
}
