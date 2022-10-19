package com.plant.web.api.member.adapter.out.persistence;

import com.google.gson.JsonObject;
import com.plant.web.api.member.application.port.out.MemberPersistenceOutPort;
import com.plant.web.api.member.domain.Member;
import com.plant.web.api.member.domain.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberPersistenceOutPort {

    private final EntityManager em;
    //private final JPAQueryFactory queryFactory;

    /*
    public MemberRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }
    */

    @Override
    /**
     * 소셜 로그인 API 요청
     * @param accessToken
     * @return
     */
    public HttpEntity<MultiValueMap<String, String>> generateProfileRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return new HttpEntity<>(headers);
    }

    /**
     * 카카오 프로필 조회
     *
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<String> requestKakaoProfile(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange("https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                request,
                String.class);
    }

    @Override
    /**
     * 네이버 프로필 조회
     * @param request
     * @return
     */
    public ResponseEntity<String> requestNaverProfile(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> reponse = null;
        try {
            reponse = restTemplate.exchange("https://openapi.naver.com/v1/nid/me",
                    HttpMethod.POST,
                    request,
                    String.class);
        } catch (HttpClientErrorException e) {
            log.info("httpStatus Error : {}" ,e);
        }

        return reponse;
    }

    /**
     * 회원가입
     * @param member
     */
    public void save(Member member) {
        log.info("회원가입");
        em.persist(member);
    }

    /**
     * snsId로 회원 조회
     * @param snsId
     * @return
     */
    //querydsl로 변경 필요!
    public Member findBySnsId(String snsId) {
        log.info("snsId로 회원 조회");
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember m = new QMember("m");

        return queryFactory
                .select(m)
                .from(m)
                .where(m.snsId.eq(snsId))
                .fetchOne();
    }

    /**
     * 닉네임 중복 체크
     * @param nickname
     * @return
     */
    public List<Member> findByNickname(String nickname) {
        log.info("닉네임 중복 체크");
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember m = new QMember("m");

        return queryFactory
                .select(m)
                .from(m)
                .where(m.nickname.eq(nickname))
                .fetch();
    }


    /**
     * 회원 탈퇴
     * @param snsid
     * @return
     */
    @Override
    public Long accountRemove(String snsid) {
        log.info("snsId로 회원 조회");
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember m = new QMember("m");

        return queryFactory.update(m)
                .set(m.delYn, 'Y')
                .where(m.snsId.eq(snsid))
                .execute();
    }
}
