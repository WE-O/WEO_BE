package com.plant.web.api.member.application.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.plant.web.api.bookmark.application.port.out.BookmarkPersistenceOutPort;
import com.plant.web.api.member.application.port.in.MemberInPort;
import com.plant.web.api.member.application.port.out.MemberPersistenceOutPort;
import com.plant.web.api.member.domain.Member;
import com.plant.web.api.member.dto.MemberDTO;
import com.plant.web.api.review.application.port.out.ReviewPersistenceOutPort;
import com.plant.web.api.scrap.application.port.out.ScrapPersistenceOutPort;
import com.plant.web.config.utill.RandomNickname;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MemberService implements MemberInPort {

    private final MemberPersistenceOutPort memberPersistenceOutPort;
    private final BookmarkPersistenceOutPort bookmarkPersistenceOutPort;
    private final ReviewPersistenceOutPort reviewPersistenceOutPort;
    private final ScrapPersistenceOutPort scrapPersistenceOutPort;

    public MemberService(MemberPersistenceOutPort memberPersistenceOutPort, BookmarkPersistenceOutPort bookmarkPersistenceOutPort, ReviewPersistenceOutPort reviewPersistenceOutPort, ScrapPersistenceOutPort scrapPersistenceOutPort) {
        this.memberPersistenceOutPort = memberPersistenceOutPort;
        this.bookmarkPersistenceOutPort = bookmarkPersistenceOutPort;
        this.reviewPersistenceOutPort = reviewPersistenceOutPort;
        this.scrapPersistenceOutPort = scrapPersistenceOutPort;
    }

    /**
     * 회원 가입
     */
    public Member join(String accessToken, String snsType) {
        Member findUsers = findByMemberId(accessToken, snsType);    //중복 회원 검증

        if(findUsers == null) {
            Member getProfile =  getProfile(accessToken, snsType);
            log.info("최초 회원 가입");
            //회원가입 진행
            //MEMBER_ID, NICKNAME, EMAIL, PROFILE_IMG, SNS_TYPE, JOIN_DATE
            //memberId, snsType, email, profileImg
            String nickname = "";

            //닉네임 중복 체크
            do {
                //임시닉네임 생성
                log.info("임시 닉네임 생성");
                nickname = RandomNickname.Nickname();
            } while (nicknameDupCheck(nickname) > 0);

            findUsers = new Member();
            findUsers.setMemberId(getProfile.getMemberId());
            findUsers.setSnsType(getProfile.getSnsType());
            findUsers.setEmail(getProfile.getEmail());
            findUsers.setNickname(nickname);
            findUsers.setProfileImg(getProfile.getProfileImg());
            // 추후 수정
            findUsers.setDelYn('N');

            //회원가입
            memberPersistenceOutPort.save(findUsers);
        }
        return findUsers;
    }

    /**
     * memberId로 기존회원 정보조회
     */
    private Member findByMemberId(String accessToken, String snsType) {
        log.info("기존 가입 회원 정보 확인");
        Member getProfile = getProfile(accessToken , snsType);
        String getMemberId = getProfile.getMemberId();
        return memberPersistenceOutPort.findByMemberId(getMemberId);
    }

    /**
     * 닉네임 중복 확인
     */
    private int nicknameDupCheck(String nickname) {
        log.info("닉네임 중복 확인");
        return memberPersistenceOutPort.findByNickname(nickname).size();
    }

    /**
     * 회원 탈퇴
     */
    @Override
    public Long accountRemove(String memberId) {
        return memberPersistenceOutPort.accountRemove(memberId);
    }

    @Override
    public Member findByMemberId(String memberId) {
        return memberPersistenceOutPort.findByMemberId(memberId);
    }

    /**
     * 프로필 조회
     */
    public Member getProfile(String accessToken, String snsType) {
        ResponseEntity<?> resultMap = null;
        JsonElement element =  null;
        Member member = new Member();
        if ("kakao".equals(snsType)) {
            // 카카오 프로필 가져오기
            resultMap = memberPersistenceOutPort.requestKakaoProfile(memberPersistenceOutPort.generateProfileRequest(accessToken));
            element = JsonParser.parseString(resultMap.getBody().toString());
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            String memberId = element.getAsJsonObject().get("id").getAsString();
            String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
            String profileImg = properties.getAsJsonObject().get("profile_image").getAsString();

            member.setSnsType(snsType);
            member.setMemberId(memberId);
            member.setEmail(email);
            member.setProfileImg(profileImg);

            System.out.println("kakao memberInfo = " + resultMap);
        } else if ("naver".equals(snsType)) {
            // 네이버 프로필 가져오기
            resultMap = memberPersistenceOutPort.requestNaverProfile(memberPersistenceOutPort.generateProfileRequest(accessToken));
            element = JsonParser.parseString(resultMap.getBody().toString());
            JsonObject naverAccount = element.getAsJsonObject().get("response").getAsJsonObject();
            String memberId = naverAccount.getAsJsonObject().get("id").getAsString();
            String email = naverAccount.getAsJsonObject().get("email").getAsString();
            String profileImg = naverAccount.getAsJsonObject().get("profile_image").getAsString();

            member.setSnsType(snsType);
            member.setMemberId(memberId);
            member.setEmail(email);
            member.setProfileImg(profileImg);

            System.out.println("naver memberInfo = " + resultMap);
        }
        return  member;
    }

    /**
     * 닉네임 수정
     * @param memberId
     * @param nickname
     * @return
     */
    public Long modifyNickname(String memberId, String nickname) {
        log.info("닉네임 수정");
        Member member = memberPersistenceOutPort.findByMemberId(memberId);
        int dupCheck = nicknameDupCheck(nickname);

        // 받아온 닉네임 중복 확인
        if(dupCheck > 0) {
            return Long.valueOf(0);
        } else {
            // 없으면 변경
            return memberPersistenceOutPort.modifyNickname(memberId, nickname);
        }
    }

    /**
     * 마이페이지 정보 조회
     * @param memberId
     * @return
     */
    public MemberDTO getMyPage(String memberId) {
        log.info("마이페이지 정보 조회");
        MemberDTO member = new MemberDTO();
        //프로필이미지, 닉네임, 이메일
        //북마크 개수, 리뷰 개수, 장소제보 개수
        //스크랩 리스트
        Member memberInfo = memberPersistenceOutPort.findByMemberId(memberId);
        member.setNickname(memberInfo.getNickname());
        member.setEmail(memberInfo.getEmail());
        member.setProfileImg(memberInfo.getProfileImg());

        int bookmarkCnt = bookmarkPersistenceOutPort.findBookmarksByMemberId(memberId).size();
        member.setBookmarkCnt(bookmarkCnt);

        int reviewCnt = reviewPersistenceOutPort.findReviewsByMemberId(memberId).size();
        member.setReviewCnt(reviewCnt);
        
        //장소제보 추후 개발 후 수정 예정
        member.setReportCnt(0);

        //스크랩 리스트 작업
        List scrapList = scrapPersistenceOutPort.findScrapsByMemberId(memberId);

        member.setScrapList(scrapList);

        return member;
    }

}
