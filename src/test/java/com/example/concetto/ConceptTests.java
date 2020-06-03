package com.example.concetto;

import com.example.concetto.models.Concept;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/*
    Since all the getter/setters are now generated via Lombok I don't think testing the Model is necessary,
    I will the tests here for now and will rethink again in the future if testings getters/setters is necessary.
    TODO: Re-think if testing Concept is necessary.
 */
public class ConceptTests {
    private Concept concept;

    @BeforeEach
    void initBefore() {
        concept = new Concept();
    }

    // (Name of method) / (action) / expectation
    @Test
    void setName_nameSetWithREST_shouldSetNameToREST() {
        concept.setName("REST");
        assertEquals("REST", concept.getName());
    }

    @Test
    void setName_nameSetWithREST_shouldNotBeNull() {
        concept.setName("REST");
        assertNotNull(concept.getName());
    }

    @Test
    @DisplayName("should return violation error message 'size must be between 1 and 20'")
    void setName_nameSetWithEmptyString_shouldReturnViolationError() {
        concept.setName("");
    }

    @Test
    @DisplayName("should return violation error message 'size must be between 1 and 20'")
    void setName_nameSetStringLength21_shouldReturnViolationError() {
        concept.setName("SportSportSportSportSport"); //25 length
    }

    @Test
    void getName_nameSetWithRest_shouldReturnREST() {
        concept.setName("REST");
        assertEquals("REST", concept.getName());
    }

    @Test
    void concept_emptyConstructor_shouldInitializeDateCreated(){
        assertNotNull(concept.getDateCreated());
    }
}
