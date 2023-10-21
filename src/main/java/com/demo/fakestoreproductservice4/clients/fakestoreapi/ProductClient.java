package com.demo.fakestoreproductservice4.clients.fakestoreapi;

import com.demo.fakestoreproductservice4.config.Config;
import com.demo.fakestoreproductservice4.dtos.FakeStoreProduct;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class ProductClient {
    // Fields
    private RestTemplate restTemplate;
    private RestTemplate restTemplateHttpClient;


    // Behaviors
    public List<FakeStoreProduct> getAllProducts() {
        ResponseEntity<FakeStoreProduct[]> responseEntity = this.restTemplate.getForEntity(Config.FAKE_STORE_API_PRODUCTS, FakeStoreProduct[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    public FakeStoreProduct getSingleProduct(Long productId) {
        ResponseEntity<FakeStoreProduct> responseEntity = this.restTemplate.getForEntity(Config.FAKE_STORE_API_PRODUCT, FakeStoreProduct.class, productId);
        return responseEntity.getBody();
    }

    public FakeStoreProduct addProduct(FakeStoreProduct newProduct) {
        ResponseEntity<FakeStoreProduct> responseEntity = this.restTemplate.postForEntity(Config.FAKE_STORE_API_PRODUCTS, newProduct, FakeStoreProduct.class);
        return responseEntity.getBody();
    }

    public FakeStoreProduct replaceProduct(Long productId, FakeStoreProduct fakeStoreProduct) {
        HttpEntity<FakeStoreProduct> requestEntity = new HttpEntity<>(fakeStoreProduct);
        ResponseEntity<FakeStoreProduct> responseEntity = this.restTemplate.exchange(Config.FAKE_STORE_API_PRODUCT, HttpMethod.PUT, requestEntity, FakeStoreProduct.class, productId);
        return responseEntity.getBody();
    }

    public FakeStoreProduct updateProduct(Long productId, FakeStoreProduct fakeStoreProduct) {
        fakeStoreProduct = this.restTemplateHttpClient.patchForObject(Config.FAKE_STORE_API_PRODUCT, fakeStoreProduct, FakeStoreProduct.class, productId);
        return fakeStoreProduct;
    }

    public FakeStoreProduct removeProduct(Long productId) {
        ResponseEntity<FakeStoreProduct> responseEntity = this.restTemplate.exchange(Config.FAKE_STORE_API_PRODUCT, HttpMethod.DELETE, null, FakeStoreProduct.class, productId);
        return responseEntity.getBody();
    }
}
