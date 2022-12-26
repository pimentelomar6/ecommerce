package com.pimentelprojects.ecommerce.repository;

import com.pimentelprojects.ecommerce.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, Long> {
}
