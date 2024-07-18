package io.hhplus.concert.domain.command;


public class WalletCommand {
    public record GetBalanceInfo(Long userId){}
    public record GetChargeInfo(Long userId, Long amount){}
}
