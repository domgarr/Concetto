package com.example.concetto.repositories;

import com.example.concetto.models.Concept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html
public interface ConceptRepository extends JpaRepository<Concept, Long> {
    @Query(value = "select * from concept as c where c.user_id = :userId", nativeQuery = true)
    List<Concept> findAllByUserId(@Param("userId") Long id);

    @Query(value="select * from concept c where c.subject_id = :subjectId", nativeQuery=true)
    List<Concept> findALlBySubjectId(@Param("subjectId") Long id);

    @Query(value="select c.* from concept as c join subject as s ON c.subject_id = s.id where datediff(curdate(), next_review_date) >= 0 AND c.subject_id = :subjectId", nativeQuery = true)
    List<Concept> findAllBySubjectIdThatAreScheduledForReview(@Param("subjectId") Long id);
}
