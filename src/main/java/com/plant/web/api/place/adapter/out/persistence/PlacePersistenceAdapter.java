package com.plant.web.api.place.adapter.out.persistence;

import com.plant.web.api.place.application.port.out.PlacePersistenceOutPort;
import com.plant.web.api.place.domain.Place;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class PlacePersistenceAdapter implements PlacePersistenceOutPort {

    private final PlaceJpaRepository placeJPARepository;

    public PlacePersistenceAdapter(PlaceJpaRepository placeJPARepository) {
        this.placeJPARepository = placeJPARepository;
    }

    @Override
    public JSONObject getKakaoPlace(String keyword) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK 46dda472d420f489cd0a5b4b363cf893");
        headers.add("Content-Type", "application/json");

        HttpEntity httpEntity = new HttpEntity(headers);

        URI uri = UriComponentsBuilder
                .fromUriString("https://dapi.kakao.com//v2/local/search/keyword")
                .queryParam("query", keyword)
                .queryParam("page", 1)
                .queryParam("size", 15)
                .build()
                .toUri();

        ParameterizedTypeReference<Map<String, Object>> typeReference = new ParameterizedTypeReference<Map<String, Object>>() {};

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(uri.toString(), HttpMethod.GET, httpEntity, typeReference);

        Map<String, Object> map = responseEntity.getBody();
        JSONObject jsonObject = new JSONObject();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.put(key, value);
        }
        return jsonObject;
    }

    @Override
    public Optional<Long> countByPlaceId(String id) {
        Optional<Long> result = placeJPARepository.countByPlaceId(id);
        return Optional.of(result.get());
    }

    @Override
    public Place save(Place place) {
        return placeJPARepository.save(place);
    }

    @Override
    public int getByViews(String id) {
        return placeJPARepository.findByViews(id);
    }

    @Override
    public int getByReviews(String id) {
        return placeJPARepository.findByReviews(id);
    }
}
