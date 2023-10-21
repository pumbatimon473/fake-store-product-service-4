package com.demo.fakestoreproductservice4.services;

import com.demo.fakestoreproductservice4.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getSingleProduct(Long id);
    Product addProduct(Product product);
    Optional<Product> updateProduct(Long productId, Product product);
    Product replaceProduct(Long productId, Product product);
    Product removeProduct(Long productId);
}
