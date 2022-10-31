package com.plant.web.api.member.domain;

import com.plant.web.api.place.domain.Place;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="TBL_REVIEW")
@Getter
@Setter
public class Review {

    @Id
    @Column(name = "review_id")
    private String reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Lob
    @Column(name = "contents")
    private String contents;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "upd_date")
    private LocalDateTime updDate;
}
