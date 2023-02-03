package com.pimentelprojects.ecommerce.services.imp;

import com.pimentelprojects.ecommerce.models.Order;
import com.pimentelprojects.ecommerce.models.UserEntity;
import com.pimentelprojects.ecommerce.repository.OrderRepository;
import com.pimentelprojects.ecommerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService {


    private OrderRepository orderRepository;
    @Autowired
    public OrderServiceImp(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public String generateNumber(){
        int num = 0;
        String sumNum = "";

        List<Order> orders = findAll();
        List<Integer> list = new ArrayList<Integer>();

        orders.stream().forEach(p -> list.add( Integer.parseInt( p.getNumber())));
        if(orders.isEmpty()){
            num = 1;
        } else {
            num = list.stream().max(Integer::compare).get();
            num++;
        }

        if(num<10){
            sumNum = "000000000" + String.valueOf(num);
        } else if(num<100){
            sumNum = "00000000" + String.valueOf(num);
        } else if(num<1000){
            sumNum = "0000000" + String.valueOf(num);
        } else if(num<10000){
            sumNum = "000000" + String.valueOf(num);
        }

        return sumNum;
    }

    @Override
    public List<Order> findByUser(UserEntity userEntity) {
        return  orderRepository.findByUserEntity(userEntity);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

}
