package io.hhplus.concert.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Concert {

    @Id @GeneratedValue
    private Long concertId;

    private String concertTitle;
    private String description;

    public Concert() {
    }

    public Concert(Long concertId, String concertTitle, String description) {
        this.concertId = concertId;
        this.concertTitle = concertTitle;
        this.description = description;
    }

}
