package com.pimentelprojects.ecommerce.controllers;


import com.pimentelprojects.ecommerce.models.Order;
import com.pimentelprojects.ecommerce.models.Product;
import com.pimentelprojects.ecommerce.services.OrderService;
import com.pimentelprojects.ecommerce.services.ProductService;
import com.pimentelprojects.ecommerce.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping
    public String home(Model model){
        List<Product> productList = productService.findAll();
        model.addAttribute("products", productList);
        return "admin/home";
    }

    @GetMapping("/users")
    public String users(Model model){
        model.addAttribute("userList", userService.findAll());
        return "admin/usuarios";
    }

    @GetMapping("/orders")
    public String orders(Model model){
        model.addAttribute("orderList", orderService.findAll());
        return "admin/ordenes";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        logger.info("Este es el id del user {}",id);
        Order order = orderService.findById(id).get();
        model.addAttribute("details", order.getOrderDetails());
        return "admin/detalleorden";
    }

    @PostMapping("search")
    public String search(@RequestParam String name, Model model, HttpSession httpSession){
        logger.info("nombre del producto: {}", name);

        List<Product> productList = productService.findAll()
                .stream().filter(p -> p.getName()
                        .contains(name))
                .collect(Collectors.toList());
        model.addAttribute("products", productList);

        //Sesion
        model.addAttribute("sesion", httpSession.getAttribute("idusuario"));
        return "admin/home";
    }





}
