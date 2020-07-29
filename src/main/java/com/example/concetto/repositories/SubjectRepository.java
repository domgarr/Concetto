package com.example.concetto.repositories;

import com.example.concetto.api.v1.model.SubjectDTO;
import com.example.concetto.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query(value = "select * from subject as s where s.user_id = :userId", nativeQuery = true)
    List<Subject> findAllSubjectByUserId(@Param("userId") Long id);

    @Modifying
    @Query(value = "update subject s set s.count = s.count + 1 where id = :id", nativeQuery = true)
    int incrementCount(@Param("id") Long id);

    @Modifying
    @Query(value = "update subject s set s.count = s.count - 1 where id = :id", nativeQuery = true)
    int decrementCount(@Param("id") Long id);

    @Modifying
    @Query(value = "update subject s set s.review_count = s.review_count + 1 where id = :id", nativeQuery = true)
    int incrementReviewCount(@Param("id") Long id);

    @Modifying
    @Query(value = "update subject s set s.review_count = s.review_count - 1 where id = :id", nativeQuery = true)
    int decrementReviewCount(@Param("id") Long id);

    @Query(value="select user_id from subject where id = :id", nativeQuery = true)
    Long findUserIdById(@Param("id") Long id);

    @Query(value="select * from subject where datediff(curdate(), last_update) > 0 AND user_id = :userId", nativeQuery = true)
    List<Subject> findAllWhereLastUpdateIsInPast(@Param("userId") Long userId);

    @Modifying
    @Query(value ="update subject s set s.next_review_date = (select next_review_date from concept where subject_id = :subjectId order by next_review_date asc limit 1)", nativeQuery = true)
    Integer updateNextReviewDateFromMostRecentConcept(@Param("subjectId") Long subjectId);

    @Query(value="select * from subject where user_id = :userId AND review_count > 0", nativeQuery = true)
    List<Subject> findAllSubjectByUserIdToReview(@Param("userId") Long userId);

    @Query(value="select * from subject where user_id = :userId AND save_count > 0", nativeQuery = true)
    List<Subject> findAllSubjectByUserIdToFinish(@Param("userId") Long userId);

    @Modifying
    @Query(value = "update subject s set s.save_count = s.save_count + 1 where id = :id", nativeQuery = true)
    int incrementSaveCount(@Param("id") Long id);

    @Modifying
    @Query(value = "update subject s set s.save_count = s.save_count - 1 where id = :id", nativeQuery = true)
    int decrementSaveCount(@Param("id") Long id);


}
