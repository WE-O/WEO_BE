package com.plant.web.api.contents.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="TBL_CONTENTS")
@Getter @Setter
public class Contents {

    @Id
    @Column(name = "contents_id")
    private String contentsId;

    @Column(name = "detail")
    private String detail;

    @Column(name = "img")
    private String img;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "upd_date")
    private LocalDateTime updDate;
}
