package com.demo.fakestoreproductservice4.util;

import com.demo.fakestoreproductservice4.models.Category;
import com.demo.fakestoreproductservice4.models.Product;
import com.demo.fakestoreproductservice4.models.Rating;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class HibernateUtil {
    // Fields
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {  // Singleton - DOUBLE-CHECKED LOCK
        if (HibernateUtil.sessionFactory == null) {
            synchronized (HibernateUtil.class) {
                if (HibernateUtil.sessionFactory == null) {
                    Configuration configuration = new Configuration();
                    configuration.setProperties(HibernateUtil.getProperties());
                    configuration.addAnnotatedClass(Category.class);
                    configuration.addAnnotatedClass(Product.class);
                    configuration.addAnnotatedClass(Rating.class);
                    StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
                    HibernateUtil.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                }
            }
        }
        return sessionFactory;
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put(Environment.URL, "jdbc:mysql://localhost:3306/fake_store_v4");
        properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.HBM2DDL_AUTO, "update");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.FORMAT_SQL, "true");
        return properties;
    }
}
