package com.pimentelprojects.ecommerce.repository;

import com.pimentelprojects.ecommerce.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);

    UserEntity findFirstByEmail(String email);
}
