package com.pimentelprojects.ecommerce.services;

import com.pimentelprojects.ecommerce.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public Product saveProduct(Product product);
    public Optional<Product> getProduct(Long id);
    public void update(Product product);
    public void detele(Long id);
    public List<Product> findAll();
}
