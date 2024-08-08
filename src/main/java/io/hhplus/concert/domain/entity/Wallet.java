package io.hhplus.concert.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Wallet {

    @Id @GeneratedValue
    @Column(name = "wallet_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long balance;

    private LocalDateTime createAt;
    private LocalDateTime lastUpdateAt;

    @Version
    private Long version;

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void setLastUpdateAt(LocalDateTime lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }

    public Wallet(Long id, User user, Long balance) {
        this.id = id;
        this.user = user;
        this.balance = balance;
    }

    public Wallet(User user, Long balance) {
        this.user = user;
        this.balance = balance;
    }

    public void update(Long balance, LocalDateTime LastUpdateAt){
        this.setBalance(balance);
        this.setLastUpdateAt(LastUpdateAt);

    }

    public Long usePoint(Long price, LocalDateTime now){
        if(price > balance) {
            throw new RuntimeException("잔고를 확인해 주세요.");
        }
        Long balanceAfter = balance - price;
        this.balance = balanceAfter;
        this.lastUpdateAt = now;

        return balanceAfter;
    }
}
