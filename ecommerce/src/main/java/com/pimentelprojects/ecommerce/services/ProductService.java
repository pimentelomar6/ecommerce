package com.pimentelprojects.ecommerce.services;

import com.pimentelprojects.ecommerce.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
     Product saveProduct(Product product);
     Optional<Product> getProduct(Long id);
    void update(Product product);
     void detele(Long id);
     List<Product> findAll();
     List<Product> searchProduct(String query);
}
