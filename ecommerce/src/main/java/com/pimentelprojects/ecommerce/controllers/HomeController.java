package com.pimentelprojects.ecommerce.controllers;


import com.pimentelprojects.ecommerce.models.Order;
import com.pimentelprojects.ecommerce.models.OrderDetails;
import com.pimentelprojects.ecommerce.models.Product;
import com.pimentelprojects.ecommerce.models.UserEntity;
import com.pimentelprojects.ecommerce.security.SecurityUtil;
import com.pimentelprojects.ecommerce.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")
public class HomeController {


    private ProductService productService;
    private UserService userService;
    private OrderService orderService;
    private OrderDetailService  orderDetailService;

    @Autowired
    public HomeController(ProductService productService,
                          UserService userService,
                          OrderService orderService,
                          OrderDetailService orderDetailService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
    }

    List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();

    Order order = new Order();

    @GetMapping("")
    public String home(Model model){

        model.addAttribute("products", productService.findAll());

        //Session
        model.addAttribute("role", SecurityUtil.role());
        model.addAttribute("username", SecurityUtil.getSessionEmail());

        if(productService.findAll().isEmpty()){
            return "user/home_empty";
        }

        return "user/home";
    }

    @GetMapping("product/{id}")
    public String productByID(@PathVariable Long id,
                              Model model){

        Product product  = productService.getProduct(id).get();

        //Session
        model.addAttribute("role", SecurityUtil.role());
        model.addAttribute("username", SecurityUtil.getSessionEmail());

        model.addAttribute("product", product);
        return "user/product";
    }

    @PostMapping("cart/add")
    public String addCart(@RequestParam Long id,
                         @RequestParam Integer cantidad,
                         Model model){
        OrderDetails orderDetails1 = new OrderDetails();

        double total = 0;

        Product product = productService.getProduct(id).get();


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

        //Session
        model.addAttribute("role", SecurityUtil.role());
        model.addAttribute("username", SecurityUtil.getSessionEmail());

        model.addAttribute("car", orderDetails);
        model.addAttribute("order", order);

        return "user/cart";
    }

    // Quitar producto del Carrito

    @GetMapping("cart/delete/{id}")
    public String deleteProductCart(@PathVariable Long id,
                                    Model model){
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
        return "redirect:/cart";
    }

    @GetMapping("cart")
    public String getCart(Model model){
        //Session
        model.addAttribute("role", SecurityUtil.role());
        model.addAttribute("username", SecurityUtil.getSessionEmail());

        if(orderDetails.isEmpty()){
            return "user/cart_empty";
        }

        model.addAttribute("car", orderDetails);
        model.addAttribute("order",order);

        return "user/cart";
    }

    @GetMapping("order")
    public String order(Model model){
        UserEntity userEntity = userService.findByEmail(SecurityUtil.getSessionEmail());

        model.addAttribute("car", orderDetails);
        model.addAttribute("order",order);
        model.addAttribute("user", userEntity);

        //Session
        model.addAttribute("role", SecurityUtil.role());
        model.addAttribute("username", SecurityUtil.getSessionEmail());
        return "user/order_resume";
    }

    @GetMapping("order/save")
    public String saveOrder(){

        order.setNumber(orderService.generateNumber());

        // Usuario
        UserEntity userEntity = userService.findByEmail(SecurityUtil.getSessionEmail());
        order.setUserEntity(userEntity);

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
    public String search(@RequestParam String name,
                         Model model){

        List<Product> productList = productService.searchProduct(name);

        model.addAttribute("products", productList);

        //Session
        model.addAttribute("role", SecurityUtil.role());
        model.addAttribute("username", SecurityUtil.getSessionEmail());

        if(productList.isEmpty()){
            return "user/home_empty";
        }

        return "user/home";
    }

}
