package com.demo.fakestoreproductservice4.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Rating extends BaseModel {
    // Fields
    private Double rate;
    private Integer count;
    @OneToOne(mappedBy = "rating")
    private Product product;

    // CTOR
    public Rating(Double rate, Integer count) {
        this.rate = rate;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "rate=" + rate +
                ", count=" + count +
                '}';
    }
}
