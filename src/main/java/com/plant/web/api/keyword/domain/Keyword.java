package com.plant.web.api.keyword.domain;

import com.plant.web.api.placeKeyword.domain.PlaceKeyword;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="TBL_KEYWORD")
@Getter
@Setter
public class Keyword {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private Long keywordId;

    @Column(name = "keyword_name")
    private String keywordName;

}
