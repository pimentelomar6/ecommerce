package com.pimentelprojects.ecommerce.controllers;


import com.pimentelprojects.ecommerce.models.Order;
import com.pimentelprojects.ecommerce.models.UserEntity;
import com.pimentelprojects.ecommerce.services.OrderService;
import com.pimentelprojects.ecommerce.services.ProductService;
import com.pimentelprojects.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private ProductService productService;
    private UserService userService;
    private OrderService orderService;

    @Autowired
    public AdminController(ProductService productService,
                           UserService userService,
                           OrderService orderService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
    }

    

    @GetMapping("/users")
    public String users(Model model){
        List<UserEntity> userEntities = userService.findAll();

        model.addAttribute("userList", userEntities);
        if(userEntities.isEmpty()){
            return "admin/users_empty";
        }
        return "admin/users";
    }

    @GetMapping("/orders")
    public String orders(Model model){
        model.addAttribute("orderList", orderService.findAll());

        if(orderService.findAll().isEmpty()){
            return "admin/orders_empty";
        }
        return "admin/orders";
    }

    @GetMapping("orders/detail/{id}")
    public String detailByID(@PathVariable("id") Long id, Model model){
        Order order = orderService.findById(id).get();
        model.addAttribute("details", order.getOrderDetails());
        return "admin/order_detail";
    }


}
