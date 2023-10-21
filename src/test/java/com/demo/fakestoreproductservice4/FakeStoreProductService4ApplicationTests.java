package com.demo.fakestoreproductservice4;

import com.demo.fakestoreproductservice4.logger.MyLogger;
import com.demo.fakestoreproductservice4.models.Category;
import com.demo.fakestoreproductservice4.models.Product;
import com.demo.fakestoreproductservice4.services.ProductService;
import com.demo.fakestoreproductservice4.services.SelfCategoryService;
import com.demo.fakestoreproductservice4.services.SelfProductService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@SpringBootTest
class FakeStoreProductService4ApplicationTests {
    // Fields
    @Autowired
    private MyLogger logger;
    @Autowired
    private SelfProductService productService;
    @Autowired
    private SelfCategoryService categoryService;

    @Test
    void contextLoads() {
    }

    /**
     * Using (cascade = CascadeType.PERSIST) in "Product.class" to create the first category "electronics".
     * NOTE: cascade must be DISABLED after running the below test "addFirstProduct()"
     * - There is no such method to add a new category in our interface "CategoryService".
     *
     */
    @Test
    void addFirstProduct() {
        // Adding a category first
        Category persistentCategory = this.categoryService.addCategory("electronics");
        Product product1 = new Product();
        product1.setTitle("Mosquito Bat");
        product1.setPrice(Double.valueOf(100));
        product1.setCategory(persistentCategory);
        product1.setDescription("Zaps the mosquitoes, small bugs and flies.");
        product1.setImage("https://www.bigbasket.com/media/uploads/p/l/40181714_1-nippo-rechargeable-mini-mosquito-bat-lithium-ion.jpg");
        logger.logInfo("SelfProductService :: addProduct() :: " + this.productService.addProduct(product1));
    }

    @Test
    void selfProductServiceGetAllProducts() {
        logger.logInfo("SelfProductService :: getAllProducts() :: " + this.productService.getAllProducts().size());
    }

    @Test
    void selfProductServiceGetSingleProduct() {
        Long productId = Long.valueOf(1);
        logger.logInfo("SelfProductService :: getSingleProduct(" + productId + ") :: " + this.productService.getSingleProduct(productId));
    }

    @Test
    void selfProductServiceAddProduct() {
        String categoryName = "electronics";
        Optional<Category> categoryOptional = this.categoryService.getCategoryByName(categoryName);
        if (categoryOptional.isEmpty()) {
            logger.logWarn(":: Test 'selfProductServiceAddProduct()' was SKIPPED! Required category 'electronics'.");
            return;
        }
        Product product1 = new Product();
        product1.setTitle("Mosquito Repellant");
        product1.setPrice(Double.valueOf(60));
        product1.setCategory(categoryOptional.get());
        product1.setDescription("Repells mosquitoes, small bugs and flies.");
        product1.setImage("https://www.bigbasket.com/media/uploads/p/l/40181714_1-nippo-rechargeable-mini-mosquito-bat-lithium-ion.jpg");
        logger.logInfo("SelfProductService :: addProduct() :: " + this.productService.addProduct(product1));

        Product product2 = new Product();
        product2.setTitle("Intel Core i7 Processor");
        product2.setPrice(Double.valueOf(500));
        product2.setCategory(categoryOptional.get());
        product2.setDescription("14th Gen Intel Core Processor Family. 65W TDP");
        product2.setImage("https://c1.neweggimages.com/ProductImageCompressAll300/19-118-466-04.jpg?ex=2");
        logger.logInfo("SelfProductService :: addProduct() :: " + this.productService.addProduct(product2));
    }

    @Test
    void selfProductServiceReplaceProduct() {
        String categoryName = "electronics";
        Optional<Category> categoryOptional = this.categoryService.getCategoryByName(categoryName);

        Product product3 = new Product();
        product3.setTitle("GIGABYTE Z790 AORUS PRO X and Intel Core i7-14700K");
        product3.setPrice(Double.valueOf(800));
        product3.setCategory(categoryOptional.get());
        product3.setDescription("Processor + Motherboard Combo");
        product3.setImage("https://c1.neweggimages.com/ProductImageCompressAll1280/combo4574744.jpg");
        Long productId = Long.valueOf(62);
        logger.logInfo("SelfProductService :: replaceProduct(" + productId + ") :: " + this.productService.replaceProduct(productId, product3));
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void selfProductServiceRemoveProduct() {
        Long productId = Long.valueOf(2);
        logger.logInfo("SelfProductService :: removeProduct(" + productId + ") :: " + this.productService.removeProduct(productId));
    }




    @Test
    void selfCategoryServiceGetCategoryByName() {
        String categoryName = "electronics";
        logger.logInfo("SelfCategoryService :: getCategoryByName(" + categoryName + ") :: " + this.categoryService.getCategoryByName(categoryName));
    }

    @Test
    void selfCategoryServiceGetProductsByCategory() {
        String categoryName = "electronics";
        logger.logInfo("SelfCategoryService :: getProductsByCategory(" + categoryName + ") :: " + this.categoryService.getProductsByCategory(new Category(categoryName)));
    }


}
