package com.pimentelprojects.ecommerce.services;

import com.pimentelprojects.ecommerce.models.Order;
import com.pimentelprojects.ecommerce.models.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll();
    Order save(Order order);
    String generateNumber();
    List<Order> findByUser(User user);

    Optional<Order> findById(Long id);
}
