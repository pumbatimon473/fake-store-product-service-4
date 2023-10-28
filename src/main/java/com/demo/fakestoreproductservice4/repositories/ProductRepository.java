package com.demo.fakestoreproductservice4.repositories;

import com.demo.fakestoreproductservice4.models.Category;
import com.demo.fakestoreproductservice4.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    List<Product> findAll();

    Optional<Product> findProductById(Long id);

    @Override
    <S extends Product> S save(S entity);

    void deleteProductById(Long aLong);

    // Example of Automatic Query: by property
    List<Product> findProductsByCategory(Category category);
    // Example of Automatic Query: by properties of a property
    List<Product> findProductsByCategoryName(String categoryName);

    // Example of Automatic Query: by properties of a property
    // Following convention: property name separated by an underscore
    List<Product> findProductsByCategory_Name(String categoryName);

    List<Product> findProductsByPriceAndCategory_Name(Double price, String categoryName);

    // Example of SQL Query (Custom Query)
    @Query(value = "SELECT * FROM product WHERE title LIKE %:title%", nativeQuery = true)
    List<Product> findProductsByTitle(String title);

    @Query(value = "SELECT ALL * FROM product WHERE description LIKE %:desc%", nativeQuery = true)
    List<Product> testCustomQuery(String desc);

    /** EXCEPTION:
     * org.springframework.dao.InvalidDataAccessResourceUsageException: Unable to find column position by name: rating_id [Column 'rating_id' not found.] [n/a]; SQL [n/a]
     *
     * Solution: You will have to define a new POJO class containing only the selected fields.
     * - https://stackoverflow.com/questions/76692158/unable-to-find-column-position-by-name-column1-the-column-name-column1-was-not
     * - It looks like you want to read only selected fields from the table. In this case you can't use the entity class in the result list.
     * - Define a new class (POJO) containing only the fields you have in the select statement.
     */
    @Query(value = "SELECT product.id, product.title, product.price, product.description, product.image FROM product INNER JOIN category ON product.category_id = category.id WHERE category.name = :categoryName", nativeQuery = true)
    List<Product> testCustomQueryJoin(String categoryName);

    // Example of JPA Query
    @Query(value = "SELECT p FROM Product p WHERE p.category.name = :categoryName")
    List<Product> testJPAQuery(String categoryName);
}
