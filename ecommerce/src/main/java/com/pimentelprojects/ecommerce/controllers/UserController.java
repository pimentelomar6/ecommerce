package com.pimentelprojects.ecommerce.controllers;

import com.pimentelprojects.ecommerce.models.Order;
import com.pimentelprojects.ecommerce.models.User;
import com.pimentelprojects.ecommerce.services.OrderService;
import com.pimentelprojects.ecommerce.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;


    BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

    @GetMapping("/register")
    public String createUser() {
        return "administrador/user/registro";
    }

    @PostMapping("/save")
    public String save(User user) {
        logger.info("usuario regitro {}", user);
        user.setTipo("USER");
        user.setPassword(bCrypt.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "administrador/user/login";
    }

    @GetMapping("/access")
    public String access(User user, HttpSession httpSession) {
        logger.info("acceso: {}", user);

        Optional<User> user1 = userService.findById(Long.parseLong(httpSession.getAttribute("idusuario").toString()));

        logger.info("usuario obtenido {}", user1.get());

        if (user1.isPresent()) {
            httpSession.setAttribute("idusuario", user1.get().getId());
            if (user1.get().getTipo().equals("ADMIN")) {
                return "redirect:/admin";
            } else {
                return "redirect:/";
            }
        } else {
            logger.info("User not exists");
        }
        return "redirect:/";
    }


    @GetMapping("/purchases")
    public String getPurchases(Model model, HttpSession httpSession) {

        model.addAttribute("sesion", httpSession.getAttribute("idusuario"));

        User user = userService.findById(Long.parseLong(httpSession.getAttribute("idusuario").toString())).get();
        List<Order> orderList = orderService.findByUser(user);

        model.addAttribute("orders", orderList);
        return "administrador/user/compras";
    }

    @GetMapping("/detail/{id}")
    public String detailPurchase(@PathVariable("id") Long id, HttpSession httpSession, Model model) {
        logger.info("id de la orden {}", id);

        Optional<Order> order = orderService.findById(id);
        model.addAttribute("details", order.get().getOrderDetails());

        //Sesion
        model.addAttribute("sesion", httpSession.getAttribute("idusuario"));


        return "administrador/user/detallecompra";
    }


    @GetMapping("/logout")
    public String logOut(HttpSession httpSession){
        httpSession.removeAttribute("idusuario");
        return "redirect:/";
    }

}