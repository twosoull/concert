package io.hhplus.concert.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Concert {

    @Id @GeneratedValue
    @Column(name = "concert_id")
    private Long id;

    private String concertTitle;
    private String description;

    private LocalDateTime concertAt;

    @Version
    private Long version = 0L;

    public Concert() {
    }

    public Concert(Long id, String concertTitle, String description) {
        this.id = id;
        this.concertTitle = concertTitle;
        this.description = description;
    }

    public Concert(Long id, String concertTitle, String description, LocalDateTime concertAt) {
        this.id = id;
        this.concertTitle = concertTitle;
        this.description = description;
        this.concertAt = concertAt;
    }
}
