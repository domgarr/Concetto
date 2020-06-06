package com.example.concetto;

import com.example.concetto.api.v1.model.ConceptDTO;
import com.example.concetto.controllers.v1.ConceptController;
import com.example.concetto.exception.NotFoundException;
import com.example.concetto.models.Concept;
import com.example.concetto.services.ConceptService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ConceptControllerTest {
    @Mock
    ConceptService conceptService;

    @InjectMocks
    private ConceptController conceptController;

    MockMvc mockMvc;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(conceptController).build();
    }

    @Test
    public void listConcepts() throws Exception {
        ConceptDTO concept1 = new ConceptDTO();
        concept1.setId(1L);
        concept1.setUserId(1L);
        concept1.setName("REST");
        concept1.setExplanation("REST stands for Representation State Transfer.");
        concept1.setReviewed(true);
        concept1.setSimplified(true);


        List<ConceptDTO> concepts = Arrays.asList(concept1);

        when(conceptService.getAllConcepts()).thenReturn(concepts);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/concepts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.concepts", hasSize(1))); //$ is the root.

    }

    @Test
    public void getConceptByUserId_GivenAInvalidUserId_ShouldThrowIsNotFound() throws Exception {
        Concept concept = new Concept();
        concept.setId(1L);

        when(conceptService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/concepts/user/4")).andExpect(status().isNotFound());
    }

    @Test
    public void getConceptByUserId_GivenAStringUserId_ShouldThrowNumberFormatException() throws Exception {
        Concept concept = new Concept();
        concept.setId(1L);

        // when(conceptService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/concepts/user/asdf")).andExpect(status().isBadRequest());
    }

    @Test
    public void saveConcept_ConceptWithEmptyDescription_ShouldThrowBadRequestError() throws Exception {
        Concept concept = new Concept();
        concept.setId(1L);
        concept.setUserId(1L);
        concept.setExplanation("Blah");

        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/concepts")
                .content(gson.toJson(concept))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveConcept_ConceptWithEmptyExplanation_ShouldThrowBadRequestError() throws Exception {
        Concept concept = new Concept();
        concept.setId(1L);
        concept.setUserId(1L);
        concept.setName("REST");
        concept.setExplanation("");

        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/concepts")
                .content(gson.toJson(concept))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveConcept_ConceptWithExplanationMaxConstraintViolation_ShouldThrowBadRequestError() throws Exception {
        Concept concept = new Concept();
        concept.setName("Placeholder");
        //length = 300
        String explanation = "CheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheck" +
                "CheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheck" +
                "CheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheckCheck";
        concept.setExplanation(explanation);
        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/concepts")
                .content(gson.toJson(concept))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveConcept_ConceptWithNameMinConstraintViolation_ShouldThrowBadRequestError() throws Exception {
        Concept concept = new Concept();
        concept.setName("");

        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/concepts")
                .content(gson.toJson(concept))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveConcept_ConceptWithNameMaxConstraintViolation_ShouldThrowBadRequestError() throws Exception {
        Concept concept = new Concept();
        concept.setName("CheckcheckcheckcheckcheckCheckcheckcheckcheckcheckCheckcheckcheckcheckcheckCheckcheckcheckcheckcheckc");

        Gson gson = new Gson();

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/concepts")
                .content(gson.toJson(concept))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
