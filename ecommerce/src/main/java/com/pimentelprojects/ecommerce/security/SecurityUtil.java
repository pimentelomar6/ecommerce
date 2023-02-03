package com.pimentelprojects.ecommerce.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;


public class SecurityUtil {
    public static String getSessionEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String email = authentication.getName();
            return  email;
        }
        return null;
    }

    public static String role(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            List<String> roles = authentication.getAuthorities().stream().map(a -> a.toString()).collect(Collectors.toList());
            if(roles.contains("ADMIN")){
                return "ADMIN";
            }
            if(roles.contains("USER")){
                return "USER";
            }
        }
        return null;
    }

}
