package com.plant.web.api.member.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="TBL_MEMBER", uniqueConstraints = {@UniqueConstraint(name="nickname_snsid_unique", columnNames = {"nickname", "sns_id"})})
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "sns_id")
    private String snsId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "sns_type")
    private String snsType;

    @Column(name = "join_date")
    private LocalDateTime joinDate;

    @Column(name = "del_date")
    private LocalDateTime delDate;

    @Column(name = "del_yn")
    private char delYn;
}
