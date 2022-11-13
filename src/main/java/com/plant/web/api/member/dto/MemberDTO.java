package com.plant.web.api.member.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MemberDTO {

    private String memberId;

    private String nickname;

    private String email;

    private String profileImg;

    private String snsType;

    private int bookmarkCnt;

    private int reviewCnt;

    private int reportCnt;

    private List scrapList;

    private LocalDateTime joinDate;

    private LocalDateTime delDate;

    private char delYn;
}
