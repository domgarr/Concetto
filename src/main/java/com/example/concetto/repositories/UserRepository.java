package com.example.concetto.repositories;

import com.example.concetto.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /*
        H2 - Exists returns boolean
        MySQL - Exists returns a BigInteger
     */
    @Query(value = "select exists(select * from user as u where u.email = :email)",
            nativeQuery = true)
    BigInteger existsByEmail(@Param("email") String email);

    @Query(value = "select * from user as u where u.email = :email",
            nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);
}
