package com.demo.fakestoreproductservice4.services;

import com.demo.fakestoreproductservice4.logger.MyLogger;
import com.demo.fakestoreproductservice4.models.Product;
import com.demo.fakestoreproductservice4.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SelfProductService implements ProductService {
    // Fields
    private ProductRepository productRepo;
    private MyLogger logger;

    @Override
    public List<Product> getAllProducts() {
        return this.productRepo.findAll();
    }

    @Override
    public Optional<Product> getSingleProduct(Long id) {
        return this.productRepo.findProductById(id);
    }

    @Override
    public Product addProduct(Product product) {
        return this.productRepo.save(product);
    }

    @Override
    public Optional<Product> updateProduct(Long productId, Product product) {
        product.setId(productId);
        return Optional.of(this.productRepo.save(product));
    }

    @Override
    public Product replaceProduct(Long productId, Product product) {
        product.setId(productId);
        return this.productRepo.save(product);
    }

    @Override
    public Product removeProduct(Long productId) {
        Optional<Product> productOptional = this.productRepo.findProductById(productId);
        if (productOptional.isEmpty()) return null;
        this.productRepo.deleteProductById(productId);
        return productOptional.get();
    }

    // Example of "query by properties of a property"
    public List<Product> getProductsByCategoryName(String categoryName) {
        return this.productRepo.findProductsByCategoryName(categoryName);
    }

    // Example of "query by properties of a property"
    // Following Convention: Property name separated by an underscore
    public List<Product> getProductsByCategory_Name(String categoryName) {
        return this.productRepo.findProductsByCategory_Name(categoryName);
    }

    public List<Product> getProductsByPriceAndCategory_Name(Double price, String categoryName) {
        return this.productRepo.findProductsByPriceAndCategory_Name(price, categoryName);
    }

    // Example of Custom Query: Native Query
    public List<Product> getProductsByTitle(String title) {
        return this.productRepo.findProductsByTitle(title);
    }

    // Example of Custom Query: Native Query
    public List<Product> getProductsMatchingDesc(String desc) {
        return this.productRepo.testCustomQuery(desc);
    }

    /** EXCEPTION:
     * org.springframework.dao.InvalidDataAccessResourceUsageException: Unable to find column position by name: rating_id [Column 'rating_id' not found.] [n/a]; SQL [n/a]
     *
     * Solution: You will have to define a new POJO class containing only the selected fields.
     * - https://stackoverflow.com/questions/76692158/unable-to-find-column-position-by-name-column1-the-column-name-column1-was-not
     * - It looks like you want to read only selected fields from the table. In this case you can't use the entity class in the result list.
     * - Define a new class (POJO) containing only the fields you have in the select statement.
     */
    public List<Product> fetchProductsByCategory(String categoryName) {
        return this.productRepo.testCustomQueryJoin(categoryName);
    }

    // Example of Custom Query: JPA QUERY (SQL like query + JAVA)
    public List<Product> pullProductsByCategory(String categoryName) {
        return this.productRepo.testJPAQuery(categoryName);
    }
}
