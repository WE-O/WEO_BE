package com.plant.web.api.scrap.domain;

import com.plant.web.api.contents.domain.Contents;
import com.plant.web.api.member.domain.Member;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="TBL_SCRAP")
@Getter @Setter
public class Scrap {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_id")
    private Long scrapId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id")
    private Contents contents;

    @CreationTimestamp
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @UpdateTimestamp
    @Column(name = "upd_date")
    private LocalDateTime updDate;

}
