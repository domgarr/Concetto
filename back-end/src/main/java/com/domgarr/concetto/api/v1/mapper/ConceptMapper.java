package com.domgarr.concetto.api.v1.mapper;

import com.domgarr.concetto.api.v1.model.ConceptDTO;
import com.domgarr.concetto.models.Concept;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ConceptMapper {
    ConceptMapper INSTANCE = Mappers.getMapper(ConceptMapper.class);

    ConceptDTO conceptToConceptDTO(Concept concept);
}
