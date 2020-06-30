package com.example.concetto.repositories;

import com.example.concetto.api.v1.model.SubjectDTO;
import com.example.concetto.models.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query(value = "select * from subject as s where s.user_id = :userId", nativeQuery = true)
    List<Subject> findAllSubjectByUserId(@Param("userId") Long id);

    @Modifying
    @Query(value = "update subject s set s.count = s.count + 1 where id = :id", nativeQuery = true)
    int incrementCount(@Param("id") Long id);

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


}
