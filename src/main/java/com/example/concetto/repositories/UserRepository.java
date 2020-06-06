package com.example.concetto.repositories;

import com.example.concetto.models.Concept;
import com.example.concetto.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select exists(select * from user as u where u.email = :email)",
            nativeQuery = true)
    Boolean existsByEmail(@Param("email") String email);
}
