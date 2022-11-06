package com.plant.web.api.bookmark.dto;

import com.plant.web.api.place.domain.Place;
import lombok.Data;

@Data
public class BookmarkDTO {

    private Long bookmarkId;

    private String memberId;

    private String placeName;

    private String roadAddressName;

    private String memo;

}
