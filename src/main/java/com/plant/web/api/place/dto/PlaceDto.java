package com.plant.web.api.place.dto;

import com.plant.web.api.bookmark.dto.BookmarkDTO;
import com.plant.web.api.place.domain.Place;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public class PlaceDto {

    @Data
    @Builder
    public static class Response {
        private String placeId;
        private String placeName;
        private String distance;
        private String placeUrl;
        private String addressName;
        private String roadAddressName;
        private String phone;
        private String categoryGroupCode;
        private String categoryGroupName;
        private String x;
        private String y;
        private int views;
        private int reviews;

        public static Response of(Place place) {
            return Response.builder()
                    .placeId(place.getPlaceId())
                    .placeName(place.getPlaceName())
                    .distance(place.getDistance())
                    .placeUrl(place.getPlaceUrl())
                    .addressName(place.getAddressName())
                    .roadAddressName(place.getRoadAddressName())
                    .phone(place.getPhone())
                    .categoryGroupCode(place.getCategoryGroupCode())
                    .categoryGroupName(place.getCategoryGroupName())
                    .x(place.getX())
                    .y(place.getY())
                    .views(place.getViews())
                    .reviews(place.getReviews())
                    .build();
        }
    }
}
