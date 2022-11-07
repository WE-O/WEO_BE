package com.plant.web.api.review.domain;

import com.plant.web.api.member.domain.Member;
import com.plant.web.api.place.domain.Place;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="TBL_REVIEW")
@Getter
@Setter
@RequiredArgsConstructor
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(name = "contents")
    private String contents;

    @CreationTimestamp
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "upd_date")
    private LocalDateTime updDate;

    @Builder
    public Review(Member member, Place place, String contents) {
        this.member = member;
        this.place = place;
        this.contents = contents;
    }
}
