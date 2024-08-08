package io.hhplus.concert.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "USERS")
@Getter
@NoArgsConstructor
public class User {

    @Id @GeneratedValue
    @Column(name="user_id")
    private Long id;
    private String password;
    private String name;


    @Version
    private Long version = 0L;

    public User(Long id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }
}
