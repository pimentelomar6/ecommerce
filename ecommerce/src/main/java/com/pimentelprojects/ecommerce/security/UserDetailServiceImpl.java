package com.pimentelprojects.ecommerce.security;

import com.pimentelprojects.ecommerce.models.User;
import com.pimentelprojects.ecommerce.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;



    @Autowired
    HttpSession httpSession;

    private Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("Esto es el username");
        Optional<User> optionalUser = userService.findByEmail(username);
        if(optionalUser.isPresent()){
            logger.info("Esto es el id del user {}", optionalUser.get().getId());
            httpSession.setAttribute("idusuario", optionalUser.get().getId());
            User user = optionalUser.get();
            return org.springframework.security.core.userdetails
                    .User.builder().username(user.getName())
                    .password(user.getPassword())
                    .roles(user.getTipo())
                    .build();
        } else {
         throw  new UsernameNotFoundException("User Not Found");
        }


    }
}
