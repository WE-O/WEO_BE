package com.plant.web.api.scrap.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScrapDTO {

    private int index;

    private Long scrapId;

    private Long contentsId;

    private String title;

    //private String img;

    private LocalDateTime regDate;

    private LocalDateTime updDate;
}
