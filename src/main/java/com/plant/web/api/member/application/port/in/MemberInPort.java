package com.plant.web.api.member.application.port.in;

import com.plant.web.api.member.domain.Member;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MemberInPort {
    ResponseEntity getProfile(String accessToken, String snsType);

    String join(Member member);
}
