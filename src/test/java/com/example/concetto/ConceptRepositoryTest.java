package com.example.concetto;

import com.example.concetto.api.v1.mapper.ConceptMapper;
import com.example.concetto.api.v1.model.ConceptDTO;
import com.example.concetto.models.Concept;
import com.example.concetto.repositories.ConceptRepository;
import com.example.concetto.services.ConceptService;
import com.example.concetto.services.ConceptServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ConceptRepositoryTest {

    ConceptService conceptService; //TODO: Use DI in the future?
    @Autowired
    ConceptRepository conceptRepository;

    @BeforeEach
    void init(){
        conceptService = new ConceptServiceImpl(ConceptMapper.INSTANCE, conceptRepository);
    }


    //TODO: The following should only be instantiated once.
    @Test
    public void getAllConceptsByUserId_WithUserIdOne_CountIsOne(){
        Concept concept = new Concept();
        concept.setName("REST");
        concept.setExplanation("Blah");
        concept.setUserId(1L);

        Concept conceptSaved = conceptRepository.save(concept);
        assertNotNull(conceptSaved);

        List<ConceptDTO> conceptDTOS = conceptService.getAllConceptsByUserId(1L);
        assertEquals(1, conceptDTOS.size());
    }
}
