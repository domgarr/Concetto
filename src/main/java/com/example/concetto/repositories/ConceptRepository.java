package com.example.concetto.repositories;

import com.example.concetto.models.Concept;
import com.example.concetto.models.CountPerDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

//https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html
public interface ConceptRepository extends JpaRepository<Concept, Long> {
    @Query(value = "select * from concept as c where c.user_id = :userId", nativeQuery = true)
    List<Concept> findAllByUserId(@Param("userId") Long id);

    @Query(value="select * from concept c where c.subject_id = :subjectId", nativeQuery=true)
    List<Concept> findALlBySubjectId(@Param("subjectId") Long id);

    @Query(value="select c.* from concept as c join subject as s ON c.subject_id = s.id where datediff(curdate(), c.next_review_date) >= 0 AND c.subject_id = :subjectId AND c.done = 1", nativeQuery = true)
    List<Concept> findAllBySubjectIdThatAreScheduledForReviewAndDone(@Param("subjectId") Long id);

    @Query(value="select * from concept as c where c.inter_interval_id = :interIntervalId", nativeQuery = true)
    Concept findByInterIntervalId(@Param("interIntervalId") Long id);

    @Query(value="select user_id from concept where id = :id", nativeQuery = true)
    Long findUserIdByConceptId(@Param("id")Long id);

    @Query(value="select count(*) from concept where subject_id = :subjectId AND  datediff(curdate(), next_review_date) >= 0 AND done = 1", nativeQuery = true)
    Integer reviewCountBySubjectId(@Param("subjectId") Long subjectId);

    @Query(value="select subject_id from concept where inter_interval_id = :interIntervalId", nativeQuery = true)
    Long findSubjectIdByInterIntervalId(@Param("interIntervalId") Long interIntervalId);

    @Query(value ="select next_review_date from concept where subject_id = :subjectId order by next_review_date asc limit 1", nativeQuery = true)
    Date findMostRecentNextReviewDate(@Param("subjectId") Long subjectId);

    @Query(value ="select * from concept where subject_id = :subjectId and NOT done = 1", nativeQuery = true)
    List<Concept> findAllBySubjectIdNotDone(@Param("subjectId") Long subjectId);

    @Query(value = "select subject_id from concept where id = :id", nativeQuery = true)
    Long findSubjectIdById(@Param("id") Long id);



    @Query(value="select count(*) from concept  where datediff( curdate(),next_review_date) > 0 AND user_id = :userId", nativeQuery = true)
    Long findCountOfPastDueConceptsByUserId(@Param("userId") Long userId);

}
