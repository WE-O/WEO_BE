package com.plant.web.api.scrap.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ScrapDTO {

    private Long scrapId;

    private Long contentsId;

    private String title;

    private String img;
}
