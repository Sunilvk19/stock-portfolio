package com.sunil.stockportfolio.repository;

import com.sunil.stockportfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String username);

    boolean existsByEmail(String email);
}
