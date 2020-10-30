package com.domgarr.concetto;

import com.domgarr.concetto.api.v1.mapper.ConceptMapper;
import com.domgarr.concetto.api.v1.model.ConceptDTO;
import com.domgarr.concetto.models.Concept;
import com.domgarr.concetto.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConceptMapperTest {
    public static final Long ID = 1L;
    public static final String NAME = "REST";
    public static final String EXPLANATION = "REST stands for Representational State Transfer";
    private static final Long USER_ID = 1L;
    ConceptMapper conceptMapper = ConceptMapper.INSTANCE;

    @Test
    public void conceptToConceptDTO() {
        Concept concept = new Concept();
        concept.setId(ID);

        concept.setUser(new User());
        concept.getUser().setId(USER_ID);

        concept.setName(NAME);
        concept.setExplanation(EXPLANATION);
        concept.setReviewed(true);
        concept.setSimplified(true);


        ConceptDTO conceptDTO = conceptMapper.conceptToConceptDTO(concept);
        assertEquals(ID, conceptDTO.getId());
        assertEquals(NAME, conceptDTO.getName());
        assertEquals(EXPLANATION, conceptDTO.getExplanation());
        assertEquals(true, conceptDTO.isReviewed());
        assertEquals(true, conceptDTO.isSimplified());
    }


}
