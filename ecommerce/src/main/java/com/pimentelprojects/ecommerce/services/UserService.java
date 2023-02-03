package com.pimentelprojects.ecommerce.services;

import com.pimentelprojects.ecommerce.dto.RegistrationDto;
import com.pimentelprojects.ecommerce.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findById(Long id);
    void save(RegistrationDto registrationDto);
    UserEntity findByEmail(String mail);
    List<UserEntity> findAll();
}
