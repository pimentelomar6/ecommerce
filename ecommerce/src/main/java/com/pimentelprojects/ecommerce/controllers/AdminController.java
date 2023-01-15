package com.pimentelprojects.ecommerce.controllers;


import com.pimentelprojects.ecommerce.models.Order;
import com.pimentelprojects.ecommerce.models.Product;
import com.pimentelprojects.ecommerce.services.OrderService;
import com.pimentelprojects.ecommerce.services.ProductService;
import com.pimentelprojects.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;


    @GetMapping
    public String home(Model model){
        List<Product> productList = productService.findAll();
        model.addAttribute("products", productList);
        return "admin/home";
    }

    @GetMapping("/users")
    public String users(Model model){
        model.addAttribute("userList", userService.findAll());
        return "admin/users";
    }

    @GetMapping("/orders")
    public String orders(Model model){
        model.addAttribute("orderList", orderService.findAll());
        return "admin/orders";
    }

    @GetMapping("/detail/{id}")
    public String detailByID(@PathVariable("id") Long id, Model model){
        Order order = orderService.findById(id).get();
        model.addAttribute("details", order.getOrderDetails());
        return "admin/orderdetail";
    }

    @PostMapping("search")
    public String search(@RequestParam String name, Model model, HttpSession httpSession){

        List<Product> productList = productService.findAll()
                .stream().filter(p -> p.getName().toLowerCase()
                        .contains(name.toLowerCase()))
                .collect(Collectors.toList());
        model.addAttribute("products", productList);

        //Sesion
        model.addAttribute("sesion", httpSession.getAttribute("idusuario"));
        return "admin/home";
    }





}
