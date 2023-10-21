package com.demo.fakestoreproductservice4.services;

import com.demo.fakestoreproductservice4.clients.fakestoreapi.CategoryClient;
import com.demo.fakestoreproductservice4.dtos.FakeStoreProduct;
import com.demo.fakestoreproductservice4.models.Category;
import com.demo.fakestoreproductservice4.models.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceProxy implements CategoryService {
    // Fields
    private CategoryClient categoryClient;

    @Override
    public List<Category> getAllCategories() {
        return this.categoryClient.getAllCategories().stream()
                .map(category -> new Category(category))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return this.categoryClient.getProductsByCategory(category.getName()).stream()
                .map(fakeStoreProduct -> this.mapToProduct(fakeStoreProduct))
                .collect(Collectors.toList());
    }

    private Product mapToProduct(FakeStoreProduct fakeStoreProduct) {
        Product product = new Product();
        product.setId(fakeStoreProduct.getId());
        product.setTitle(fakeStoreProduct.getTitle());
        product.setPrice(fakeStoreProduct.getPrice());
        product.setCategory(new Category(fakeStoreProduct.getCategory()));
        product.setDescription(fakeStoreProduct.getDescription());
        product.setImage(fakeStoreProduct.getImage());
        return product;
    }
}
