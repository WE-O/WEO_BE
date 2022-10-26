package com.plant.web.api.member.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@DynamicInsert
@Entity
@Table(name="TBL_MEMBER", uniqueConstraints = {@UniqueConstraint(name="nickname_snsid_unique", columnNames = {"nickname", "sns_id"})})
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private String id;

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

    @CreationTimestamp
    @Column(name = "join_date")
    private LocalDateTime joinDate;

    @Column(name = "del_date")
    private LocalDateTime delDate;

    @ColumnDefault("'N'")
    @Column(name = "del_yn")
    private char delYn;
}
