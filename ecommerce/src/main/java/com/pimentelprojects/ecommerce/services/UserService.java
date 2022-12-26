package com.pimentelprojects.ecommerce.services;

import com.pimentelprojects.ecommerce.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    User save(User user);

    Optional<User> findByEmail(String mail);

    List<User> findAll();
}
