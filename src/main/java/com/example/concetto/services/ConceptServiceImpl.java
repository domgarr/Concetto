package com.example.concetto.services;

import com.example.concetto.api.v1.mapper.ConceptMapper;
import com.example.concetto.api.v1.model.ConceptDTO;
import com.example.concetto.models.Concept;
import com.example.concetto.exception.NotFoundException;
import com.example.concetto.models.CountPerDate;
import com.example.concetto.repositories.ConceptRepository;
import com.example.concetto.repositories.CountPerDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConceptServiceImpl implements ConceptService {
    private final ConceptMapper conceptMapper;
    private final ConceptRepository conceptRepository;
    @Autowired
    private CountPerDateRepository countPerDateRepository;

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
    public List<ConceptDTO> findAllConceptsBySubjectId(Long subjectId) {
        return conceptRepository.findALlBySubjectId(subjectId)
                .stream()
                .map(conceptMapper::conceptToConceptDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ConceptDTO findDtoById(Long id) {

        Optional<Concept> conceptOptional = conceptRepository.findById(id);

        if (!conceptOptional.isPresent()) {
            throw new NotFoundException("Concept not found.");
        }

        return conceptOptional.map(conceptMapper::conceptToConceptDTO).get();
    }

    @Override
    public Concept findById(Long id) {
        Optional<Concept> conceptOptional = conceptRepository.findById(id);

        if (!conceptOptional.isPresent()) {
            throw new NotFoundException("Concept not found.");
        }
        return conceptOptional.get();
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

    @Override
    public List<ConceptDTO> findAllConceptsBySubjectIdScheduledForReview(Long id) {
        return conceptRepository.findAllBySubjectIdThatAreScheduledForReviewAndDone(id)
                .stream()
                .map(conceptMapper::conceptToConceptDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Concept findByInterIntervalId(Long id) {
        return conceptRepository.findByInterIntervalId(id);
    }

    @Override
    public Long findUserIdByConceptId(Long id) {
        return conceptRepository.findUserIdByConceptId(id);
    }

    @Override
    public Integer reviewCountBySubjectId(Long subjectId) {
        return conceptRepository.reviewCountBySubjectId(subjectId);
    }

    @Override
    public Long findSubjectIdByInterIntervalId(Long interIntervalId) {
        return conceptRepository.findSubjectIdByInterIntervalId(interIntervalId);
    }

    @Override
    public Date findMostRecentNextReviewDate(Long subjectId) {
        return conceptRepository.findMostRecentNextReviewDate(subjectId);
    }

    @Override
    public List<ConceptDTO> findAllBySubjectIdNotDone(Long subjectId) {
        return conceptRepository.findAllBySubjectIdNotDone(subjectId)
                .stream()
                .map(conceptMapper :: conceptToConceptDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long findSubjectIdById(Long id) {
        return conceptRepository.findSubjectIdById(id);
    }

    @Override
    public List<CountPerDate> findConceptReviewCountPerDate(Long userId) {
        boolean isFirstDateTodayDate = false;
        List<CountPerDate> countPerDates = countPerDateRepository.findCountOfNextSevenDueConceptsByUserId(userId);
        Long pastDueCount = conceptRepository.findCountOfPastDueConceptsByUserId(userId);

        if(!countPerDates.isEmpty()){
            SimpleDateFormat parser = new SimpleDateFormat("d MMM yyyy");
            //Check to see if an object containing the current date exists.
            Date today = new Date();
            Date firstDateInList = countPerDates.get(0).getReviewDate();

            if(parser.format(today).compareTo(parser.format(firstDateInList)) == 0){
                isFirstDateTodayDate = true;
                Long summedCount = countPerDates.get(0).getCount() + pastDueCount;
                countPerDates.get(0).setCount(summedCount);
            }
        }

        if(!isFirstDateTodayDate || countPerDates.isEmpty()){
            if(pastDueCount > 0){
                CountPerDate countPerDate = new CountPerDate();
                countPerDate.setReviewDate(new Date());
                countPerDate.setCount(pastDueCount);

                countPerDates.add(0, countPerDate);
            }
        }

        if(countPerDates.size() != 7){
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat parser = new SimpleDateFormat("d MMM yyyy");

            for(int i = 0; i < 7 ; i++){
                cal.setTime(new Date());
                cal.add(Calendar.DATE, i);
                Date curDate = cal.getTime();

                if(countPerDates.size() - 1 < i ){
                    countPerDates.add(i, new CountPerDate(curDate, 0L));
                    continue;
                }

                Date curReviewDate = countPerDates.get(i).getReviewDate();
                if(parser.format(curDate).compareTo(parser.format(curReviewDate)) != 0) {
                    countPerDates.add(i, new CountPerDate(curDate, 0L));
                }
            }
        }
        return countPerDates;
    }
}