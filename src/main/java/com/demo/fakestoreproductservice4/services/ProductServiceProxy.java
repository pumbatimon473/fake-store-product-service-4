package com.demo.fakestoreproductservice4.services;

import com.demo.fakestoreproductservice4.clients.fakestoreapi.ProductClient;
import com.demo.fakestoreproductservice4.dtos.FakeStoreProduct;
import com.demo.fakestoreproductservice4.models.Category;
import com.demo.fakestoreproductservice4.models.Product;
import com.demo.fakestoreproductservice4.models.Rating;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceProxy implements ProductService {
    // Fields
    private ProductClient productClient;

    // CTOR
    public ProductServiceProxy(ProductClient productClient) {
        this.productClient = productClient;
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productClient.getAllProducts().stream()
                .map(this::mapToProduct)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> getSingleProduct(Long id) {
        return Optional.ofNullable(this.mapToProduct(this.productClient.getSingleProduct(id)));
    }

    @Override
    public Product addProduct(Product product) {
        FakeStoreProduct newProduct = this.mapToFakeStoreProduct(product);
        newProduct = this.productClient.addProduct(newProduct);
        return this.mapToProduct(newProduct);
    }

    @Override
    public Optional<Product> updateProduct(Long productId, Product product) {
        FakeStoreProduct fakeStoreProduct = this.mapToFakeStoreProduct(product);
        fakeStoreProduct = this.productClient.updateProduct(productId, fakeStoreProduct);
        return Optional.ofNullable(this.mapToProduct(fakeStoreProduct));
    }

    @Override
    public Product replaceProduct(Long productId, Product product) {
        FakeStoreProduct fakeStoreProduct = this.mapToFakeStoreProduct(product);
        fakeStoreProduct = this.productClient.replaceProduct(productId, fakeStoreProduct);
        return this.mapToProduct(fakeStoreProduct);
    }

    @Override
    public Product removeProduct(Long productId) {
        FakeStoreProduct removedProduct = this.productClient.removeProduct(productId);
        return this.mapToProduct(removedProduct);
    }

    private Product mapToProduct(FakeStoreProduct fakeStoreProduct) {
        if (fakeStoreProduct == null) return null;
        Product product = new Product();
        product.setId(fakeStoreProduct.getId());
        product.setTitle(fakeStoreProduct.getTitle());
        product.setPrice(fakeStoreProduct.getPrice());
        product.setCategory(new Category(fakeStoreProduct.getCategory()));
        product.setDescription(fakeStoreProduct.getDescription());
        product.setImage(fakeStoreProduct.getImage());
        if (fakeStoreProduct.getRating() != null) {
            product.setRating(new Rating(fakeStoreProduct.getRating().getRate(), fakeStoreProduct.getRating().getCount()));
        }
        return product;
    }

    private FakeStoreProduct mapToFakeStoreProduct(Product product) {
        FakeStoreProduct fakeStoreProduct = new FakeStoreProduct();
        fakeStoreProduct.setTitle(product.getTitle());
        fakeStoreProduct.setPrice(product.getPrice());
        fakeStoreProduct.setCategory(product.getCategory().getName());
        fakeStoreProduct.setDescription(product.getDescription());
        fakeStoreProduct.setImage(product.getImage());
        return fakeStoreProduct;
    }
}
