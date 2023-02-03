package com.pimentelprojects.ecommerce.services.imp;


import com.pimentelprojects.ecommerce.dto.RegistrationDto;
import com.pimentelprojects.ecommerce.models.Role;
import com.pimentelprojects.ecommerce.models.UserEntity;
import com.pimentelprojects.ecommerce.repository.RoleRepository;
import com.pimentelprojects.ecommerce.repository.UserRepository;
import com.pimentelprojects.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {


    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(RegistrationDto registrationDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(registrationDto.getId());
        userEntity.setName(registrationDto.getName());
        userEntity.setEmail(registrationDto.getEmail());
        userEntity.setAddress(registrationDto.getAddress());
        userEntity.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = roleRepository.findByName("USER");
        userEntity.setRoles(Arrays.asList(role));
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity findByEmail(String mail) {
        return userRepository.findByEmail(mail);
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
}
