package com.example.concetto;

import com.example.concetto.controllers.v1.ConceptController;
import com.example.concetto.exception.NotFoundException;
import com.example.concetto.models.Concept;
import com.example.concetto.models.User;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


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
    public void getConceptByUserId_GivenAInvalidUserId_ShouldThrowIsNotFound() throws Exception {
        Concept concept = new Concept();
        concept.setId(1L);

        when(conceptService.findDtoById(anyLong())).thenThrow(NotFoundException.class);

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

        concept.setUser(new User());
        concept.getUser().setId(1L);

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

        concept.setUser(new User());
        concept.getUser().setId(1L);

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
