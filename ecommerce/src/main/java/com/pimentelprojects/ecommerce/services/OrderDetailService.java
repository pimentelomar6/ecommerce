package com.pimentelprojects.ecommerce.services;


import com.pimentelprojects.ecommerce.models.OrderDetails;

public interface OrderDetailService {
    OrderDetails save(OrderDetails orderDetails);
}
