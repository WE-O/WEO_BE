package com.plant.web.api.bookmark.domain;

import com.plant.web.api.member.domain.Member;
import com.plant.web.api.place.domain.Place;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="TBL_BOOKMARK", uniqueConstraints = @UniqueConstraint(columnNames = {"bookmark_id", "member_id", "place_id"})
)
@Getter
@Setter
public class Bookmark {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long bookmarkId;

    @ManyToOne(fetch = FetchType.LAZY)  //연관관계의 주인. 값은 여기서 넣음
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(name = "memo")
    private String memo;

    @Column(name = "bookmark_yn")
    private String bookmarkYN;

    @CreationTimestamp
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @UpdateTimestamp
    @Column(name = "upd_date")
    private LocalDateTime updDate;

//    private int index;
}
