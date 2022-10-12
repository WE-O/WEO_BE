package bside.weo.plant.application.domain.member;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    // USER_ID, SNS_ID, NICKNAME, EMAIL, PROFILE_IMG, SNS_TYPE, JOIN_DATE
    private String snsId;

    private String nickname;

    private String email;

    private String profileImg;

    private String snsType;

    private String joinDate;
}
