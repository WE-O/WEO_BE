package com.plant.web.api.member.application.port.in;

import org.springframework.http.ResponseEntity;

public interface MemberInPort {
    ResponseEntity getProfile(String accessToken, String snsType);
}
