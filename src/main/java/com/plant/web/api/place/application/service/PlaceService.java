package com.plant.web.api.place.application.service;

import com.plant.web.api.bookmark.adapter.out.persistence.BookmarkJpaRepository;
import com.plant.web.api.bookmark.application.port.out.BookmarkPersistenceOutPort;
import com.plant.web.api.bookmark.application.service.BookmarkService;
import com.plant.web.api.bookmark.domain.Bookmark;
import com.plant.web.api.member.adapter.out.persistence.MemberJpaRepository;
import com.plant.web.api.member.application.service.MemberService;
import com.plant.web.api.member.domain.Member;
import com.plant.web.api.place.adapter.out.persistence.PlaceJpaRepository;
import com.plant.web.api.place.application.port.in.PlaceInPort;
import com.plant.web.api.place.application.port.out.PlacePersistenceOutPort;
import com.plant.web.api.place.domain.Place;
import com.plant.web.api.place.dto.PlaceDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PlaceService implements PlaceInPort {

    private final MemberService memberService;
    private final BookmarkService bookmarkService;
    private final PlaceJpaRepository placeJpaRepository;
    private final PlacePersistenceOutPort placePersistenceOutPort;
    private final BookmarkPersistenceOutPort bookmarkPersistenceOutPort;

    @Override
    public JSONObject getPlaces(String keyword, String memberId) {
        JSONObject getPlaces = savePlace(keyword, memberId);
        return getPlaces;
    }

    @Override
    public PlaceDto.Response getPlaceDetails(String placeId, HttpServletRequest request, HttpServletResponse response) {
        Place outPortByPlace = placePersistenceOutPort.getByPlaceId(placeId);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                log.info("cookie.getName = " + cookie.getName());
                log.info("cookie.getValue = " + cookie.getValue());
                if (!cookie.getValue().contains(placeId)) {
                    cookie.setValue(placeId);
                    cookie.setMaxAge(60 * 60 * 24);  /* 쿠키 시간 24시간 */
                    response.addCookie(cookie);
                    outPortByPlace.setViews(outPortByPlace.getViews() + 1);
                    placePersistenceOutPort.save(outPortByPlace);
                }
            }
        } else {
            Cookie newCookie = new Cookie("placeId_" + outPortByPlace.getPlaceId(), placeId);
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
            outPortByPlace.setViews(outPortByPlace.getViews() + 1);
            placePersistenceOutPort.save(outPortByPlace);
        }
        Place place = placePersistenceOutPort.getByPlaceId(placeId);

        return PlaceDto.Response.of(place);
    }

    /**
     * 카카오 API 에서 받아오는 데이터가 없을경우 저장
     */
    private JSONObject savePlace(String keyword, String memberId) {

        String changKeyword = keyword.replaceAll("\\s", "");
        JSONObject kakaoPlace = placePersistenceOutPort.getKakaoPlace(changKeyword);
        List<String> list = (List<String>) kakaoPlace.get("documents");
        JSONArray jsonArray = new JSONArray();
        JSONArray resultJsonArr = new JSONArray();
        JSONObject result = new JSONObject();
        //리팩토링
        for (int i = 0; i < list.size(); i++) {
            jsonArray.add(list.get(i));
        }
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = (Map<String, Object>) jsonArray.get(i);
            Optional<Long> idIndex = placePersistenceOutPort.countByPlaceId((String) map.get("id"));
            if (idIndex.get() < 1) {
                Place board = new Place();
                board.setAddressName((String) map.get("address_name"));
                board.setCategoryGroupCode((String) map.get("category_group_code"));
                board.setCategoryGroupName((String) map.get("category_group_name"));
                board.setCategoryGroupName((String) map.get("category_name"));
                board.setDistance((String) map.get("distance"));
                board.setPlaceId((String) map.get("id"));
                board.setPhone((String) map.get("phone"));
                board.setPlaceName((String) map.get("place_name"));
                board.setPlaceUrl((String) map.get("place_url"));
                board.setRoadAddressName((String) map.get("road_address_name"));
                board.setX((String) map.get("x"));
                board.setY((String) map.get("y"));
                board.setViews(0);
                board.setReviews(0);

                placePersistenceOutPort.save(board);

                resultJsonArr.add(list.get(i));
            } else {
                int view = placePersistenceOutPort.getByViews((String) map.get("id"));
                int review = placePersistenceOutPort.getByReviews((String) map.get("id"));
                Map<String, Object> resultMap = new HashMap<>();

                resultMap.put("view", view);
                resultMap.put("review", review);
                resultMap.put("id", (map.get("id")));
                resultMap.put("place_name", (map.get("place_name")));
                resultMap.put("category_name", (map.get("category_name")));
                resultMap.put("road_address_name", (map.get("road_address_name")));

                if (!ObjectUtils.isEmpty(memberId)) {
                    Member member = memberService.findByMemberId(memberId);
                    String placeId = (String) map.get("id");

                    if (!ObjectUtils.isEmpty(placeId)) {
                        Place place = this.findById(placeId);
                        Bookmark bookmark = bookmarkService.findByMemberAndPlace(member, place);
                        if(!ObjectUtils.isEmpty(bookmark)){
                            String bookmarkYN = bookmark.getBookmarkYN();
                            resultMap.put("bookmark", bookmarkYN);
                        }
                    }
                }
                resultJsonArr.add(resultMap);
            }
        }

        result.put("documents", resultJsonArr);

        return result;
    }

    public Place findById(String memberId) {
        return placeJpaRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("없음"));
    }
}
