package com.plant.web.api.bookmark.dto;

import com.plant.web.api.place.domain.Place;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookmarkDTO {

    private int index;

    private Long bookmarkId;

    private String memberId;

    private String placeId;

    private String placeName;

    private String roadAddressName;

    private String memo;

    private String bookmarkYN;

    private LocalDateTime regDate;

    private LocalDateTime updDate;

}
