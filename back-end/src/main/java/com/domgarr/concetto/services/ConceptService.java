package com.domgarr.concetto.services;

import com.domgarr.concetto.api.v1.model.ConceptDTO;
import com.domgarr.concetto.models.Concept;
import com.domgarr.concetto.models.CountPerDate;

import java.util.Date;
import java.util.List;


public interface ConceptService {
    List<ConceptDTO> getAllConcepts();
    List<ConceptDTO> getAllConceptsByUserId(Long id);
    List<ConceptDTO> findAllConceptsBySubjectId(Long id);
    ConceptDTO findDtoById(Long id);
    Concept findById(Long id);
    Concept save(Concept concept);
    List<ConceptDTO> findAllConceptsBySubjectIdScheduledForReview(Long id);
    Concept findByInterIntervalId(Long id);
    Long findUserIdByConceptId(Long id);
    Integer reviewCountBySubjectId(Long subjectId);
    Long findSubjectIdByInterIntervalId(Long interIntervalId);
    Date findMostRecentNextReviewDate(Long subjectId);
    List<ConceptDTO> findAllBySubjectIdNotDone(Long subjectId);
    Long findSubjectIdById(Long id);
    List<CountPerDate> findConceptReviewCountPerDate(Long userId);
    void deleteById(Long id);
}
