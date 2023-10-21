package com.demo.fakestoreproductservice4.services;

import com.demo.fakestoreproductservice4.models.Category;
import com.demo.fakestoreproductservice4.models.Product;
import com.demo.fakestoreproductservice4.repositories.CategoryRepository;
import com.demo.fakestoreproductservice4.repositories.ProductRepository;
import com.demo.fakestoreproductservice4.util.HibernateUtil;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SelfCategoryService implements CategoryService {
    // Fields
    private CategoryRepository categoryRepo;
    private ProductRepository productRepo;

    public Category addCategory(String categoryName) {
        return this.categoryRepo.save(new Category(categoryName));
    }

    @Override
    public List<Category> getAllCategories() {
        return this.categoryRepo.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        // Fetch persistent category
        Optional<Category> categoryOptional = this.categoryRepo.findCategoryByName(category.getName());
        if (categoryOptional.isEmpty()) return null;
        return this.productRepo.findProductsByCategory(categoryOptional.get());
    }

    /**
     * FAILED: throws org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.demo.fakestoreproductservice4.models.Category.products: could not initialize proxy - no Session
     * - https://www.baeldung.com/hibernate-initialize-proxy-exception
     * @param category
     * @return
     */
    public List<Product> getProductsByCategory_FAILED(Category category) {
        // APPROACH 1: FAILED: throws org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role
        // could not initialize proxy - no Session
        // Fetch persistent category
        Optional<Category> categoryOptional = this.categoryRepo.findCategoryByName(category.getName());
        if (categoryOptional.isEmpty()) return null;
        return categoryOptional.get().getProducts();
    }

    /**
     * Using Hibernate SessionFactory: From Baeldung - *** FAILED
     * - https://www.baeldung.com/hibernate-initialize-proxy-exception
     * - Section: 3.4 Fetching Roles
     *
     * @param category
     * @return
     */
    public List<Product> getProductsByCategory_FAILED_2(Category category) {
        // FAILED: org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.demo.fakestoreproductservice4.models.Category.products: could not initialize proxy - no Session
        Optional<Category> categoryOptional = this.categoryRepo.findCategoryByName(category.getName());
        if (categoryOptional.isEmpty()) return null;
        Long categoryId = categoryOptional.get().getId();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Category persistentCategory = session.find(Category.class, categoryId);
        List<Product> productList = persistentCategory.getProducts();
        session.getTransaction().commit();
        session.close();
        return productList;
    }

    public Optional<Category> getCategoryByName(String categoryName) {
        return this.categoryRepo.findCategoryByName(categoryName);
    }
}
