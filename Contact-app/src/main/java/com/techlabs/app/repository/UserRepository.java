package com.techlabs.app.repository;

import com.techlabs.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByUsername(String username);

   //boolean existsUserByEmail(String email);

    Optional<User> findByUsername(String username);
}
