package com.pimentelprojects.ecommerce.controllers;

import com.pimentelprojects.ecommerce.models.Order;
import com.pimentelprojects.ecommerce.models.UserEntity;
import com.pimentelprojects.ecommerce.security.SecurityUtil;
import com.pimentelprojects.ecommerce.services.OrderService;
import com.pimentelprojects.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private OrderService orderService;


    @Autowired
    public UserController(UserService userService,
                          OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;;
    }


    @GetMapping("/purchases")
    public String getPurchases(Model model) {

        //Session
        model.addAttribute("role", SecurityUtil.role());
        model.addAttribute("username", SecurityUtil.getSessionEmail());

        UserEntity userEntity = userService.findByEmail(SecurityUtil.getSessionEmail());
        List<Order> orderList = orderService.findByUser(userEntity);

        if(orderList.isEmpty()){
            return "user/purchases_empty";
        }
        model.addAttribute("orders", orderList);
        return "user/purchases";
    }

    @GetMapping("/purchases/detail/{id}")
    public String purchaseDetail(@PathVariable("id") Long id,
                                 Model model) {

        //Session
        model.addAttribute("role", SecurityUtil.role());
        model.addAttribute("username", SecurityUtil.getSessionEmail());

        Optional<Order> order = orderService.findById(id);
        UserEntity userEntity = userService.findByEmail(SecurityUtil.getSessionEmail());

        if(order.get().getUserEntity().getId() == userEntity.getId()){
            model.addAttribute("details", order.get().getOrderDetails());
        }

        return "user/purchase_detail";
    }


}
