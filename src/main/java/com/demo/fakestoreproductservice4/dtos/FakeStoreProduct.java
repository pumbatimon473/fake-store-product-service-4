package com.demo.fakestoreproductservice4.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProduct {
    // Fields
    private Long id;
    private String title;
    private Double price;
    private String category;
    private String description;
    private String image;
    private FakeStoreRatingDto rating;
}
