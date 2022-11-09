package com.plant.web.api.bookmark.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyBookmarkRequest {

    private String memberId;

    private String placeId;

    private String bookmarkYN;
}
