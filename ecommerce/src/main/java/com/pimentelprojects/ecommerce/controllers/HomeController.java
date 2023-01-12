package com.pimentelprojects.ecommerce.controllers;


import com.pimentelprojects.ecommerce.models.Order;
import com.pimentelprojects.ecommerce.models.OrderDetails;
import com.pimentelprojects.ecommerce.models.Product;
import com.pimentelprojects.ecommerce.models.User;
import com.pimentelprojects.ecommerce.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService  orderDetailService;
    List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();

    Order order = new Order();

    @GetMapping("")
    public String home(Model model, HttpSession httpSession){

        logger.info("Sesion del usuario {}", httpSession.getAttribute("idusuario"));
        model.addAttribute("products", productService.findAll());

        //Sesion
        model.addAttribute("sesion", httpSession.getAttribute("idusuario"));


        return "administrador/user/home";
    }

    @GetMapping("productohome/{id}")
    public String productHome(@PathVariable Long id, Model model, HttpSession httpSession){
        logger.info("Id enviado como parametro {}", id);
        Product product = new Product();
        Optional<Product> productOptional = productService.getProduct(id);
        product = productOptional.get();

        //Sesion
        model.addAttribute("sesion", httpSession.getAttribute("idusuario"));


        model.addAttribute("product", product);
        return "administrador/user/productohome";
    }

    @PostMapping("car")
    public String addCar(@RequestParam Long id,
                         @RequestParam Integer cantidad,
                         Model model,
                         HttpSession httpSession){
        OrderDetails orderDetails1 = new OrderDetails();
        Product product = new Product();
        double total = 0;

        Optional<Product> optionalProduct = productService.getProduct(id);
        logger.info("Producto añadido {}", optionalProduct.get());
        logger.info("cantidad {}", cantidad);
        product = optionalProduct.get();

        orderDetails1.setQuantity(cantidad);
        orderDetails1.setPrice(product.getPrice());
        orderDetails1.setName(product.getName());
        orderDetails1.setTotal(product.getPrice() * cantidad);
        orderDetails1.setProduct(product);


        // Validar que el producto no se añada mas de una vez
        Long idProduct = product.getId();
        boolean isAdded = orderDetails.stream().anyMatch(p -> p.getProduct().getId()==idProduct);

        if(!isAdded){
            orderDetails.add(orderDetails1);
        }

        total = orderDetails.stream().mapToDouble(dt -> dt.getTotal()).sum();

        order.setTotal(total);

        //Sesion
        model.addAttribute("sesion", httpSession.getAttribute("idusuario"));

        model.addAttribute("car", orderDetails);
        model.addAttribute("order", order);


        return "administrador/user/carrito";
    }

    // Quitar producto del Carrito

    @GetMapping("delete/car/{id}")
    public String deleteProductCar(@PathVariable Long id, Model model){
        //Nueva lista de Productos
        List<OrderDetails> newOrders = new ArrayList<OrderDetails>();

        for (OrderDetails details: orderDetails) {
            if(details.getProduct().getId() != id){
                newOrders.add(details);
            }
        }

        orderDetails = newOrders;
        double total = 0;
        total = orderDetails.stream().mapToDouble(p -> p.getTotal()).sum();

        order.setTotal(total);
        model.addAttribute("car", orderDetails);
        model.addAttribute("order",order);
        return "redirect:/getCar";
    }

    @GetMapping("getCar")
    public String getCar(Model model, HttpSession httpSession){
        //Sesion
        model.addAttribute("sesion", httpSession.getAttribute("idusuario"));



        if(orderDetails.isEmpty()){
            return "administrador/user/carritoempty";
        }

        model.addAttribute("car", orderDetails);
        model.addAttribute("order",order);

        return "administrador/user/carrito";
    }

    @GetMapping("order")
    public String order(Model model, HttpSession httpSession){
        User user = userService.findById( Long.parseLong(httpSession.getAttribute("idusuario").toString())).get();

        model.addAttribute("car", orderDetails);
        model.addAttribute("order",order);
        model.addAttribute("user",user);

        //Sesion
        model.addAttribute("sesion", httpSession.getAttribute("idusuario"));


        return "administrador/user/resumenorden";
    }

    @GetMapping("saveOrder")
    public String saveOrder(HttpSession httpSession){
        Date dateCreated = new Date();
        order.setDateCreate(dateCreated);
        order.setNumber(orderService.generateNumber());

        // Usuario
        User user = userService.findById( Long.parseLong(httpSession.getAttribute("idusuario").toString()) ).get();
        order.setUser(user);

        orderService.save(order);

        // Guardar Detalles
        for (OrderDetails details:
             orderDetails) {
            details.setOrder(order);
            orderDetailService.save(details);
        }

        // Limpiar order y listas
        order = new Order();
        orderDetails.clear();
        return "redirect:/";
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
        return "administrador/user/home";
    }

}
