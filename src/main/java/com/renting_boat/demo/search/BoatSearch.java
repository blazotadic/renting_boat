package com.renting_boat.demo.search;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class BoatSearch {

    private String categoryContain;
    private Double priceLess;
    private Double priceGreater;
    private String brandContain;
    private Date freeAfter;
}
