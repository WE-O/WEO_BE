package com.plant.web.api.member.domain;

import com.plant.web.api.place.domain.Place;
import lombok.Data;

@Data
public class BookmarkDTO {

    private String bookmarkId;

    private String memberId;

    private String placeName;

    private String roadAddressName;

    private String memo;

}
