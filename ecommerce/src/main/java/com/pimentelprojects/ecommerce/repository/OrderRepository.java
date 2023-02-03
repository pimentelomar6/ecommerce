package com.pimentelprojects.ecommerce.repository;

import com.pimentelprojects.ecommerce.models.Order;
import com.pimentelprojects.ecommerce.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserEntity(UserEntity userEntity);
}
