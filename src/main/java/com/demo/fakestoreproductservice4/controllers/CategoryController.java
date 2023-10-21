package com.demo.fakestoreproductservice4.controllers;

import com.demo.fakestoreproductservice4.dtos.ProductDto;
import com.demo.fakestoreproductservice4.dtos.RatingDto;
import com.demo.fakestoreproductservice4.exceptions.ClientErrorException;
import com.demo.fakestoreproductservice4.exceptions.ProductNotFoundException;
import com.demo.fakestoreproductservice4.logger.MyLogger;
import com.demo.fakestoreproductservice4.models.Category;
import com.demo.fakestoreproductservice4.models.Product;
import com.demo.fakestoreproductservice4.services.CategoryService;
import com.demo.fakestoreproductservice4.services.CategoryServiceProxy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class CategoryController {
    // Fields
    private static final String errMsg = ":: Exception caught in method %s | %s | %s";
    private CategoryService categoryService;
    private MyLogger logger;

    // CTOR
    public CategoryController(CategoryServiceProxy categoryServiceProxy, MyLogger logger) {
        this.categoryService = categoryServiceProxy;
        this.logger = logger;
    }

    // Behaviors
    @GetMapping("/categories")
    public HttpEntity<List<String>> getAllCategories() throws ClientErrorException {
        HttpEntity<List<String>> response;
        try {
            List<String> categories = this.categoryService.getAllCategories().stream()
                    .map(category -> category.getName())
                    .collect(Collectors.toList());
            response = new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = String.format(CategoryController.errMsg, "getAllCategories()", e.getClass(), e.getMessage());
            this.logger.logError(errorMessage);
            throw new ClientErrorException(201, errorMessage);
        }
        return response;
    }

    @GetMapping("/category/{categoryName}")
    public HttpEntity<List<ProductDto>> getProductsByCategory(@PathVariable(name = "categoryName") String categoryName) throws ProductNotFoundException, ClientErrorException {
        HttpEntity<List<ProductDto>> response;
        try {
            List<ProductDto> products = this.categoryService.getProductsByCategory(new Category(categoryName)).stream()
                    .map(product -> this.mapToProductDto(product))
                    .collect(Collectors.toList());
            response = new ResponseEntity<>(products, HttpStatus.OK);
        } catch (NullPointerException e) {
            String errorMessage = String.format(CategoryController.errMsg, "getProductsByCategory()", e.getClass(), e.getMessage());
            this.logger.logWarn(errorMessage);
            throw new ProductNotFoundException(202, "There is no product with category (" + categoryName + ")");
        } catch (Exception e) {
            String errorMessage = String.format(CategoryController.errMsg, "getProductsByCategory()", e.getClass(), e.getMessage());
            this.logger.logError(errorMessage);
            throw new ClientErrorException(203, errorMessage);
        }
        return response;
    }

    private ProductDto mapToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(product.getCategory().getName());
        productDto.setDescription(product.getDescription());
        productDto.setImage(product.getImage());
        if (product.getRating() != null)
            productDto.setRating(new RatingDto(product.getRating().getRate(), product.getRating().getCount()));
        return productDto;
    }
}
