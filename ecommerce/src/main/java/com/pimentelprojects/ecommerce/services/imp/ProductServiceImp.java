package com.pimentelprojects.ecommerce.services.imp;

import com.pimentelprojects.ecommerce.models.Product;
import com.pimentelprojects.ecommerce.repository.ProductRepository;
import com.pimentelprojects.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImp implements ProductService {


    private ProductRepository productRepository;
    @Autowired
    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);

    }

    @Override
    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);

    }

    @Override
    public void update(Product product) {
        productRepository.save(product);
    }

    @Override
    public void detele(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> searchProduct(String query) {
        return productRepository.searchProduct(query);
    }
}
