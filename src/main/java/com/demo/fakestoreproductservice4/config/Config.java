package com.demo.fakestoreproductservice4.config;

import com.demo.fakestoreproductservice4.FakeStoreProductService4Application;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class Config {
    // Fields
    public static final String FAKE_STORE_API_HOST = "https://fakestoreapi.com";
    public static final String FAKE_STORE_API_PRODUCTS = FAKE_STORE_API_HOST + "/products";
    public static final String FAKE_STORE_API_PRODUCT = FAKE_STORE_API_HOST + "/products/{id}";
    public static final String FAKE_STORE_API_CATEGORIES = FAKE_STORE_API_HOST + "/products/categories";
    public static final String FAKE_STORE_API_PRODUCTS_BY_CATEGORY = FAKE_STORE_API_HOST + "/products/category/{categoryName}";

    // Beans
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    RestTemplate restTemplateHttpClient(RestTemplateBuilder builder) {
        return builder.requestFactory(HttpComponentsClientHttpRequestFactory.class).build();
    }

    @Bean
    Logger logger() {
        return LoggerFactory.getLogger(FakeStoreProductService4Application.class);
    }

    /**
     * Hibernate SessionFactory:
     *
     * References:
     * - https://stackoverflow.com/questions/41703110/configuring-the-sessionfactory-in-springboot-spring-data-jpa-mysql-project
     * - https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/The-right-MySQL-persistencexml-example-file-for-JPA-22-and-Hibernate-5
     * - https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/3-ways-to-build-a-Hibernate-SessionFactory-in-Java-by-example
     * - https://github.com/eugenp/tutorials/blob/master/persistence-modules/hibernate-exceptions/src/main/java/com/baeldung/hibernate/exception/transientobject/HibernateUtil.java
     * - https://www.baeldung.com/hibernate-initialize-proxy-exception
     *
     * @return
     */

    /* NOT WORKING

    @Bean("dataSource")
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/fake_store_v4");
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("root");
        return dataSourceBuilder.build();
    }

    @Bean
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder sessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionFactoryBuilder.addProperties(this.getHibernateProperties());
        sessionFactoryBuilder.addPackages("ORM.Model");
        return sessionFactoryBuilder.buildSessionFactory();
    }

    public Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.hbm2ddl.auto", "create");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        return properties;
    }

    @Bean
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return transactionManager;
    }

     */
}
