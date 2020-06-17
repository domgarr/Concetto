package com.example.concetto.services;

import com.example.concetto.api.v1.mapper.SubjectMapper;
import com.example.concetto.api.v1.model.SubjectDTO;
import com.example.concetto.models.Subject;
import com.example.concetto.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
    SubjectRepository subjectRepository;
    SubjectMapper subjectMapper;

    public SubjectServiceImpl( SubjectMapper subjectMapper, SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    @Override
    public SubjectDTO save(Subject subject) {

        Subject savedSubject = subjectRepository.save(subject);
        return subjectMapper.subjectToSubjectDTO(savedSubject);
    }

    @Override
    public List<SubjectDTO> findAllByUserId(Long id) {
        return subjectRepository.findAllSubjectByUserId(id).stream()
                .map(subjectMapper :: subjectToSubjectDTO)
                .collect(Collectors.toList());
    }


}
