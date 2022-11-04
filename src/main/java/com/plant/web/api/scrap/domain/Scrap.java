package com.plant.web.api.scrap.domain;

import com.plant.web.api.contents.domain.Contents;
import com.plant.web.api.member.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="TBL_SCRAP")
@Getter @Setter
public class Scrap {

    @Id
    @Column(name = "scrap_id")
    private String scrapId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id")
    private Contents contents;

}
