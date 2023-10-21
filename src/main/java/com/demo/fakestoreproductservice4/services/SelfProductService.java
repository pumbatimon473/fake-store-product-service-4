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
}
