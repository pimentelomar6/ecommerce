package com.pimentelprojects.ecommerce.repository;

import com.pimentelprojects.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE lower(p.name) LIKE lower(CONCAT('%', :query,'%') )")
    List<Product> searchProduct(String query);

}
