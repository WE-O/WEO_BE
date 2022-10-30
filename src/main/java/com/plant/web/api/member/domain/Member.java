package com.plant.web.api.member.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DynamicInsert
@Entity
@Table(name="TBL_MEMBER")
@Getter @Setter
public class Member {

    @Id
    @Column(name = "member_id")
    private String memberId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "sns_type")
    private String snsType;

    @CreationTimestamp
    @Column(name = "join_date")
    private LocalDateTime joinDate;

    @Column(name = "del_date")
    private LocalDateTime delDate;

    @ColumnDefault("'N'")
    @Column(name = "del_yn")
    private char delYn;

}
