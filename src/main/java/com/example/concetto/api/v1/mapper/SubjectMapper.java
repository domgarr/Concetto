package com.example.concetto.api.v1.mapper;

import com.example.concetto.api.v1.model.SubjectDTO;
import com.example.concetto.models.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface SubjectMapper {
    SubjectMapper INSTANCE = Mappers.getMapper(SubjectMapper.class);
    SubjectDTO subjectToSubjectDTO(Subject subject);
}
