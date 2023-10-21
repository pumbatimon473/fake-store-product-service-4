package com.demo.fakestoreproductservice4.clients.fakestoreapi;

import com.demo.fakestoreproductservice4.config.Config;
import com.demo.fakestoreproductservice4.dtos.FakeStoreProduct;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class CategoryClient {
    // Fields
    private RestTemplate restTemplate;

    // Behaviors
    public List<String> getAllCategories() {
        ResponseEntity<String[]> responseEntity = this.restTemplate.getForEntity(Config.FAKE_STORE_API_CATEGORIES, String[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    public List<FakeStoreProduct> getProductsByCategory(String category) {
        ResponseEntity<FakeStoreProduct[]> responseEntity = this.restTemplate.getForEntity(Config.FAKE_STORE_API_PRODUCTS_BY_CATEGORY, FakeStoreProduct[].class, category);
        return Arrays.asList(responseEntity.getBody());
    }
}
