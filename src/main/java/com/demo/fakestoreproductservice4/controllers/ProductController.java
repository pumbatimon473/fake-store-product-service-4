package com.demo.fakestoreproductservice4.controllers;

import com.demo.fakestoreproductservice4.dtos.ErrorDto;
import com.demo.fakestoreproductservice4.dtos.ProductDto;
import com.demo.fakestoreproductservice4.dtos.RatingDto;
import com.demo.fakestoreproductservice4.exceptions.ClientErrorException;
import com.demo.fakestoreproductservice4.exceptions.ProductNotFoundException;
import com.demo.fakestoreproductservice4.logger.MyLogger;
import com.demo.fakestoreproductservice4.models.Category;
import com.demo.fakestoreproductservice4.models.Product;
import com.demo.fakestoreproductservice4.services.ProductService;
import com.demo.fakestoreproductservice4.services.ProductServiceProxy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    // Fields
    private static final String errMsg = ":: Exception caught in method: %s | %s | %s";
    private ProductService productService;
    private MyLogger logger;

    // CTOR
    public ProductController(ProductServiceProxy productServiceProxy, MyLogger logger) {
        this.productService = productServiceProxy;
        this.logger = logger;
    }

    // Behaviors
    @GetMapping
    public HttpEntity<List<ProductDto>> getAllProducts() throws ClientErrorException {
        HttpEntity<List<ProductDto>> response;
        try {
            response = new ResponseEntity<>(this.productService.getAllProducts().stream()
                    .map(this::mapProductToDto)
                    .collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = String.format(ProductController.errMsg, "getAllProducts()", e.getClass(), e.getMessage());
            this.logger.logError(errorMessage);
            throw new ClientErrorException(101, errorMessage);
        }
        return response;
    }

    @GetMapping("/{id}")
    public HttpEntity<ProductDto> getSingleProduct(@PathVariable(name = "id") Long productId) throws ClientErrorException, ProductNotFoundException {
        HttpEntity<ProductDto> response;
        try {
            Optional<Product> productOptional = productService.getSingleProduct(productId);
            if (productOptional.isEmpty()) throw new NullPointerException("Product with id (" + productId + ") does not exist!");
            response = new ResponseEntity<>(this.mapProductToDto(productOptional.get()), HttpStatus.OK);
        } catch (NullPointerException e) {
            String errorMessage = String.format(ProductController.errMsg, "getSingleProduct()", e.getClass(), e.getMessage());
            this.logger.logWarn(errorMessage);
            throw new ProductNotFoundException(102, e.getMessage());
        } catch (Exception e) {
            String errorMessage = String.format(ProductController.errMsg, "getSingleProduct()", e.getClass(), e.getMessage());
            this.logger.logError(errorMessage);
            throw new ClientErrorException(103, errorMessage);
        }
        return response;
    }

    @PostMapping
    public HttpEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) throws ClientErrorException {
        HttpEntity<ProductDto> response;
        try {
            Product newProduct = this.mapToProduct(productDto);
            newProduct = this.productService.addProduct(newProduct);
            response = new ResponseEntity<>(this.mapProductToDto(newProduct), HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = String.format(ProductController.errMsg, "addProduct()", e.getClass(), e.getMessage());
            this.logger.logError(errorMessage);
            throw new ClientErrorException(104, errorMessage);
        }
        return response;
    }

    @PutMapping("/{id}")
    public HttpEntity<ProductDto> replaceProduct(@PathVariable(name = "id") Long productId, @RequestBody ProductDto productDto) throws ClientErrorException {
        HttpEntity<ProductDto> response;
        try {
            Product savedProduct = this.productService.replaceProduct(productId, this.mapToProduct(productDto));
            response = new ResponseEntity<>(this.mapProductToDto(savedProduct), HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = String.format(ProductController.errMsg, "replaceProduct()", e.getClass(), e.getMessage());
            this.logger.logError(errorMessage);
            throw new ClientErrorException(105, errorMessage);
        }
        return response;
    }

    @PatchMapping("/{id}")
    public HttpEntity<ProductDto> updateProduct(@PathVariable(name = "id") Long productId, @RequestBody ProductDto productDto) throws ProductNotFoundException, ClientErrorException {
        HttpEntity<ProductDto> response;
        try {
            Product product = this.mapToProduct(productDto);
            Optional<Product> productOptional = this.productService.updateProduct(productId, product);
            if (productOptional.isEmpty()) throw new NullPointerException("Product with id (" + productId + ") does not exist!");
            response = new ResponseEntity<>(this.mapProductToDto(productOptional.get()), HttpStatus.OK);
        } catch (NullPointerException e) {
            String errorMessage = String.format(ProductController.errMsg, "updateProduct()", e.getClass(), e.getMessage());
            this.logger.logWarn(errorMessage);
            throw new ProductNotFoundException(106, e.getMessage());
        } catch (Exception e) {
            String errorMessage = String.format(ProductController.errMsg, "updateProduct", e.getClass(), e.getMessage());
            this.logger.logError(errorMessage);
            throw new ClientErrorException(106, errorMessage);
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ProductDto> removeProduct(@PathVariable(name = "id") Long productId) throws ProductNotFoundException, ClientErrorException {
        HttpEntity<ProductDto> response;
        try {
            Product removedProduct = this.productService.removeProduct(productId);
            response = new ResponseEntity<>(this.mapProductToDto(removedProduct), HttpStatus.OK);
        } catch (NullPointerException e) {
            String errorMessage = String.format(ProductController.errMsg, "removeProduct()", e.getClass(), e.getMessage());
            this.logger.logWarn(errorMessage);
            throw new ProductNotFoundException(107, "Product with id (" + productId + ") does not exist!");
        } catch (Exception e) {
            String errorMessage = String.format(ProductController.errMsg, "removeProduct()", e.getClass(), e.getMessage());
            this.logger.logError(errorMessage);
            throw new ClientErrorException(107, errorMessage);
        }
        return response;
    }

    /**
     * Handling Exception: Method 1 - At the controller level
     * @ExceptionHandler(MyException.class)
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public HttpEntity<ErrorDto> handleProductNotFoundException(ProductNotFoundException e) {
        return new ResponseEntity<>(new ErrorDto(e.getErrCode(), e.getErrMsg()), HttpStatus.NOT_FOUND);
    }

    private Product mapToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setCategory(new Category(productDto.getCategory()));
        product.setDescription(productDto.getDescription());
        product.setImage(productDto.getImage());
        return product;
    }

    private ProductDto mapProductToDto(Product product) {
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
