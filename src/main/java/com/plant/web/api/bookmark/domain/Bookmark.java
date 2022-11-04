package com.plant.web.api.bookmark.domain;

import com.plant.web.api.member.domain.Member;
import com.plant.web.api.place.domain.Place;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="TBL_BOOKMARK")
@Getter
@Setter
public class Bookmark {

    @Id
    @Column(name = "bookmark_id")
    private String bookmarkId;

    @ManyToOne(fetch = FetchType.LAZY)  //연관관계의 주인. 값은 여기서 넣음
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(name = "memo")
    private String memo;
}
