package com.demo.fakestoreproductservice4.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RatingDto {
    // Fields
    private Double rate;
    private Integer count;
}
