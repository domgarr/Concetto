package com.example.concetto.controllers.v1;

import com.example.concetto.models.Concept;
import com.example.concetto.models.InterInterval;
import com.example.concetto.models.Subject;
import com.example.concetto.services.ConceptService;
import com.example.concetto.services.InterIntervalService;
import com.example.concetto.services.SubjectService;
import com.example.concetto.utility.DateUtility;
import com.example.concetto.utility.IntervalUtility;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;

@RestController()
@RequestMapping("/api/v1/interval")
public class InterIntervalController {
    private final InterIntervalService interIntervalService;
    private final ConceptService conceptService;
    private final SubjectService subjectService;
    private final DateUtility dateUtility;

    public InterIntervalController(InterIntervalService interIntervalService, ConceptService conceptService, SubjectService subjectService, DateUtility dateUtility) {
        this.interIntervalService = interIntervalService;
        this.conceptService = conceptService;
        this.subjectService = subjectService;
        this.dateUtility = dateUtility;
    }

    //TODO: Return a InterIntervalDTO.
    @PostMapping("/calculate")
    @Transactional
    public InterInterval calculateNextInterInterval(@RequestBody InterInterval interInterval){
        InterInterval fetchedInterInterval = interIntervalService.find(interInterval.getId());
        InterInterval updatedInterInterval = IntervalUtility.calculateNextInterval(interInterval.getResponseRating(), fetchedInterInterval);
        Concept fetchedConcept = conceptService.findByInterIntervalId(interInterval.getId());


        //Using the newly calculated length (CalculateNextInterval) add to the nextReviewDate and update the concept.
        fetchedConcept.setNextReviewDate(dateUtility.addDaysToDate(fetchedConcept.getNextReviewDate(), interInterval.getLength()));
        conceptService.save(fetchedConcept);

        Long subjectId = conceptService.findSubjectIdByInterIntervalId(updatedInterInterval.getId());
        subjectService.decrementReviewCount(subjectId);
        subjectService.updateNextReviewDateFromMostRecentConcept(subjectId);

        return interIntervalService.save(updatedInterInterval);
    }
}
