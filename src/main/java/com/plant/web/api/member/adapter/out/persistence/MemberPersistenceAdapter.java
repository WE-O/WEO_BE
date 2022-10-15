package com.plant.web.api.member.adapter.out.persistence;

import com.plant.web.api.member.application.port.out.MemberPersistenceOutPort;
import com.plant.web.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
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

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    //querydsl로 변경 필요!
    public List<Member> findBySnsId(String snsId) {
        return em.createQuery("select u from Member u where u.snsId = :snsId", Member.class)
                .setParameter("snsId", snsId)
                .getResultList();
    }

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

        return restTemplate.exchange("https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                request,
                String.class);
    }
}
