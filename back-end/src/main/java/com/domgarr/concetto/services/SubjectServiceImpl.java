package com.domgarr.concetto.services;

import com.domgarr.concetto.api.v1.mapper.SubjectMapper;
import com.domgarr.concetto.api.v1.model.SubjectDTO;
import com.domgarr.concetto.exception.NotFoundException;
import com.domgarr.concetto.repositories.SubjectRepository;
import com.domgarr.concetto.models.Subject;
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
    public List<SubjectDTO> findAllDtoByUserId(Long id) {
        return subjectRepository.findAllSubjectByUserId(id).stream()
                .map(subjectMapper :: subjectToSubjectDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Subject> findAllByUserId(Long id) {
        return subjectRepository.findAllSubjectByUserId(id);
    }

    @Override
    public SubjectDTO findById(Long id) {
        Optional<Subject> subjectOptional = subjectRepository.findById(id);
        if(!subjectOptional.isPresent()){
            throw new NotFoundException("Subject not found.");
        }
        return subjectOptional.map(subjectMapper::subjectToSubjectDTO).get();
    }

    @Override
    public int incrementCount(Long id) {
        return subjectRepository.incrementCount(id);
    }

    @Override
    public int incrementReviewCount(Long id) {
        return subjectRepository.incrementReviewCount(id);
    }

    @Override
    public int decrementReviewCount(Long id) {
        return subjectRepository.decrementReviewCount(id);
    }

    @Override
    public Long findUserIdById(Long id) {
        Long fetchedId = subjectRepository.findUserIdById(id);
        if(fetchedId == null){
            throw new NotFoundException("Subject with the given id does not exist.");
        }
        return fetchedId;
    }

    @Override
    public List<Subject> findAllWhereLastUpdateIsInPast(Long userId) {
        return subjectRepository.findAllWhereLastUpdateIsInPast(userId);
    }

    @Override
    public List<Subject> saveAll(List<Subject> subjects) {
        return subjectRepository.saveAll(subjects);
    }

    @Override
    public Integer updateNextReviewDateFromMostRecentConcept(Long subjectId) {
        return subjectRepository.updateNextReviewDateFromMostRecentConcept(subjectId);
    }

    @Override
    public List<SubjectDTO> findAllSubjectByUserIdAndDone(Long userId) {
        return subjectRepository.findAllSubjectByUserIdToReview(userId).stream()
                .map(subjectMapper :: subjectToSubjectDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubjectDTO> findAllSubjectByUserIdAndSaved(Long userId) {
        return subjectRepository.findAllSubjectByUserIdToFinish(userId).stream()
                .map(subjectMapper :: subjectToSubjectDTO)
                .collect(Collectors.toList());
    }

    @Override
    public int incrementSaveCount(Long id) {
        return subjectRepository.incrementSaveCount(id);
    }

    @Override
    public int decrementSaveCount(Long id) {
        return subjectRepository.decrementSaveCount(id);
    }
}
