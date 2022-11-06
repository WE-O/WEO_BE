package com.plant.web.api.contents.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="TBL_CONTENTS")
@Getter @Setter
public class Contents {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contents_id")
    private Long contentsId;

    @Column(name = "title")
    private String title;

    @Column(name = "detail")
    private String detail;

    @Column(name = "img")
    private String img;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "upd_date")
    private LocalDateTime updDate;
}
