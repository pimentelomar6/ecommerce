package com.pimentelprojects.ecommerce.repository;

import com.pimentelprojects.ecommerce.models.Order;
import com.pimentelprojects.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
