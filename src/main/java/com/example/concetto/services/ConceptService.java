package com.example.concetto.services;

import com.example.concetto.api.v1.model.ConceptDTO;
import com.example.concetto.models.Concept;

import java.util.List;


public interface ConceptService {
    List<ConceptDTO> getAllConcepts();
    List<ConceptDTO> getAllConceptsByUserId(Long id);
    List<ConceptDTO> findAllConceptsBySubjectId(Long id);
    ConceptDTO findById(Long id);
    Concept save(Concept concept);
}
