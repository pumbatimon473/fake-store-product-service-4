package com.demo.fakestoreproductservice4.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product extends BaseModel {
    // Fields
    private String title;
    private Double price;
    @ManyToOne  // (cascade = CascadeType.PERSIST)
    private Category category;
    private String description;
    private String image;
    @OneToOne
    private Rating rating;

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", rating=" + rating +
                '}';
    }
}
