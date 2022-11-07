package com.plant.web.api.placeKeyword.domain;

import com.plant.web.api.keyword.domain.Keyword;
import com.plant.web.api.place.domain.Place;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="TBL_PLACE_KEYWORD")
@Getter
@Setter
@RequiredArgsConstructor
public class PlaceKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_keyword_id")
    private Long placeKeywordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    /*
    private List<Keyword> keywords = new ArrayList<>();

    @Builder
    public PlaceKeyword(Place place, List<Keyword> keywords) {
        this.place = place;
        this.keywords = keywords;
    }
    */

}
