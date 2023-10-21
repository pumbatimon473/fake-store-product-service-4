package com.demo.fakestoreproductservice4.repositories;

import com.demo.fakestoreproductservice4.models.Category;
import com.demo.fakestoreproductservice4.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    List<Product> findAll();

    Optional<Product> findProductById(Long id);

    @Override
    <S extends Product> S save(S entity);

    void deleteProductById(Long aLong);

    List<Product> findProductsByCategory(Category category);
}
