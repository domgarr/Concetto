package com.example.concetto.services;

import com.example.concetto.api.v1.model.SubjectDTO;
import com.example.concetto.models.Subject;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SubjectService {
    SubjectDTO save (Subject subject);
    List<SubjectDTO> findAllDtoByUserId(Long id);
    List<Subject> findAllByUserId(Long id);
    SubjectDTO findById(Long id);
    int incrementCount(Long id);
    int incrementReviewCount(Long id);
    int decrementReviewCount(Long id);
    Long findUserIdById(Long id);
    List<Subject> findAllWhereLastUpdateIsInPast(Long userId);
    List<Subject> saveAll(List<Subject> subjects);
    Integer updateNextReviewDateFromMostRecentConcept(Long subjectId);
    List<SubjectDTO> findAllSubjectByUserIdAndDone(Long userId);
    List<SubjectDTO> findAllSubjectByUserIdAndSaved(Long userId);
    int incrementSaveCount(Long id);
    int decrementSaveCount(Long id);
}
