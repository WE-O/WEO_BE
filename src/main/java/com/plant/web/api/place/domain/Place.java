package com.plant.web.api.place.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TBL_PLACE", uniqueConstraints = {@UniqueConstraint(name = "place_id_unique", columnNames = {"place_id"})})
@Getter
@Setter
public class Place {

    @Id
    @Column(name = "place_id")
    private String placeId;

    @Column(name = "place_name")
    private String placeName;

    @Column(name = "distance")
    private String distance;

    @Column(name = "place_url")
    private String placeUrl;

    @Column(name = "address_name")
    private String addressName;

    @Column(name = "road_address_name")
    private String roadAddressName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "category_group_code")
    private String categoryGroupCode;

    @Column(name = "category_group_name")
    private String categoryGroupName;

    @Column(name = "x")
    private String x;

    @Column(name = "y")
    private String y;

    @Column(name = "views")
    private int views;

    @Column(name = "reviews")
    private int reviews;
}
