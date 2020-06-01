package com.example.concetto.repositories;

import com.example.concetto.models.Concept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html
public interface ConceptRepository extends JpaRepository<Concept, Long> {
    @Query(value = "select * from concept as c where c.user_id = :userId",
            nativeQuery = true)
    List<Concept> findAllByUserId(@Param("userId") Long id);
}