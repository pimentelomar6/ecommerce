package com.pimentelprojects.ecommerce.services.imp;


import com.pimentelprojects.ecommerce.models.OrderDetails;
import com.pimentelprojects.ecommerce.repository.OrderDetailRepository;
import com.pimentelprojects.ecommerce.services.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImp implements OrderDetailService {


    private OrderDetailRepository orderDetailRepository;
    @Autowired
    public OrderDetailServiceImp(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public OrderDetails save(OrderDetails orderDetails) {
        return orderDetailRepository.save(orderDetails);
    }
}
