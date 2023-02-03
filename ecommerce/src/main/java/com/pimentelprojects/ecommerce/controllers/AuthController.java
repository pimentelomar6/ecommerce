package com.pimentelprojects.ecommerce.controllers;

import com.pimentelprojects.ecommerce.dto.RegistrationDto;
import com.pimentelprojects.ecommerce.models.UserEntity;
import com.pimentelprojects.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



import javax.validation.Valid;


@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/register")
    public String createUser(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user",user);

        return "user/register";
    }

    @PostMapping("/register")
    public String save(@Valid @ModelAttribute("user") RegistrationDto user,
                       BindingResult result, Model model
                       ) {

        if(result.hasErrors()){
            model.addAttribute("user",user);
            return "user/register";
        }

        UserEntity existingUserEmail = userService.findByEmail(user.getEmail());
        if(existingUserEmail != null && existingUserEmail.getEmail() != null &&
           !existingUserEmail.getEmail().isEmpty()){
            return "redirect:/register?fail";
        }

        userService.save(user);
        return "redirect:/?success";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }


}
