package com.pimentelprojects.ecommerce.services;


import com.pimentelprojects.ecommerce.models.OrderDetails;
import com.pimentelprojects.ecommerce.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImp implements OrderDetailService{

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetails save(OrderDetails orderDetails) {
        return orderDetailRepository.save(orderDetails);
    }
}
