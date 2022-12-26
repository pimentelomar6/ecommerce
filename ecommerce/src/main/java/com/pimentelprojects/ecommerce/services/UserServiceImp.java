package com.pimentelprojects.ecommerce.services;


import com.pimentelprojects.ecommerce.models.User;
import com.pimentelprojects.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String mail) {
        return userRepository.findByMail(mail);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
