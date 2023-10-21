package com.demo.fakestoreproductservice4.repositories;

import com.demo.fakestoreproductservice4.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Override
    List<Category> findAll();

    Optional<Category> findCategoryByName(String name);

    @Override
    <S extends Category> S save(S entity);
}
