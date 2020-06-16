package com.example.concetto.services;

import com.example.concetto.api.v1.mapper.ConceptMapper;
import com.example.concetto.api.v1.model.ConceptDTO;
import com.example.concetto.models.Concept;
import com.example.concetto.exception.NotFoundException;
import com.example.concetto.repositories.ConceptRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConceptServiceImpl implements ConceptService {
    private final ConceptMapper conceptMapper;
    private final ConceptRepository conceptRepository;

    public ConceptServiceImpl(ConceptMapper conceptMapper, ConceptRepository conceptRepository) {
        this.conceptMapper = conceptMapper;
        this.conceptRepository = conceptRepository;
    }

    @Override
    public List<ConceptDTO> getAllConcepts() {
        return conceptRepository.findAll()
                .stream()
                .map(conceptMapper::conceptToConceptDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ConceptDTO> getAllConceptsByUserId(Long id) {


        return conceptRepository.findAllByUserId(id)
                .stream()
                .map(conceptMapper::conceptToConceptDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ConceptDTO findById(Long id) {

        Optional<Concept> conceptOptional = conceptRepository.findById(id);

        if (!conceptOptional.isPresent()) {
            throw new NotFoundException("Concept not found.");
        }

        return conceptOptional.map(conceptMapper::conceptToConceptDTO).get();
    }

    @Override
    public Concept save(Concept concept) {

        if (concept.isReviewed() && concept.isSimplified()) {
            concept.setDone(true);
        }

        //TODO: Should save the return value from the repository and return that object in the return statement;
        conceptRepository.save(concept);
        return concept;
    }


}
